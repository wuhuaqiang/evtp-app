/*
Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
*/

package main

import (
	"crypto/md5"
	"encoding/json"
	"fmt"
	"github.com/hyperledger/fabric/core/chaincode/shim"
	pb "github.com/hyperledger/fabric/protos/peer"
	"strconv"
	"strings"
	"time"
)

// ============================================================================================================================
// write() - genric write variable into ledger
//
// Shows Off PutState() - writting a key/value into the ledger
//
// Inputs - Array of strings
//    0   ,    1
//   key  ,  value
//  "abc" , "test"
// ============================================================================================================================
func write(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	var key, value string
	var err error
	fmt.Println("starting write")

	if len(args) != 2 {
		return shim.Error("Incorrect number of arguments. Expecting 2. key of the variable and value to set")
	}

	// input sanitation
	err = sanitize_arguments(args)
	if err != nil {
		return shim.Error(err.Error())
	}

	key = args[0] //rename for funsies
	value = args[1]
	err = stub.PutState(key, []byte(value)) //write the variable into the ledger
	if err != nil {
		return shim.Error(err.Error())
	}

	fmt.Println("- end write")
	return shim.Success(nil)
}

// ============================================================================================================================
// delete_marble() - remove a marble from state and from marble index
//
// Shows Off DelState() - "removing"" a key/value from the ledger
//
// Inputs - Array of strings
//      0      ,         1
//     id      ,  authed_by_company
// "m999999999", "united marbles"
// ============================================================================================================================
func delete_marble(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	fmt.Println("starting delete_marble")

	if len(args) != 2 {
		return shim.Error("Incorrect number of arguments. Expecting 2")
	}

	// input sanitation
	err := sanitize_arguments(args)
	if err != nil {
		return shim.Error(err.Error())
	}

	id := args[0]
	authed_by_company := args[1]

	// get the marble
	marble, err := get_marble(stub, id)
	if err != nil {
		fmt.Println("Failed to find marble by id " + id)
		return shim.Error(err.Error())
	}

	// check authorizing company (see note in set_owner() about how this is quirky)
	if marble.Owner.Company != authed_by_company {
		return shim.Error("The company '" + authed_by_company + "' cannot authorize deletion for '" + marble.Owner.Company + "'.")
	}

	// remove the marble
	err = stub.DelState(id) //remove the key from chaincode state
	if err != nil {
		return shim.Error("Failed to delete state")
	}

	fmt.Println("- end delete_marble")
	return shim.Success(nil)
}

// ============================================================================================================================
// Init Marble - create a new marble, store into chaincode state
//
// Shows off building a key's JSON value manually
//
// Inputs - Array of strings
//      0      ,    1  ,  2  ,      3          ,       4
//     id      ,  color, size,     owner id    ,  authing company
// "m999999999", "blue", "35", "o9999999999999", "united marbles"
// ============================================================================================================================
func init_marble(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	var err error
	fmt.Println("starting init_marble")

	if len(args) != 5 {
		return shim.Error("Incorrect number of arguments. Expecting 5")
	}

	//input sanitation
	err = sanitize_arguments(args)
	if err != nil {
		return shim.Error(err.Error())
	}

	id := args[0]
	color := strings.ToLower(args[1])
	owner_id := args[3]
	authed_by_company := args[4]
	size, err := strconv.Atoi(args[2])
	if err != nil {
		return shim.Error("3rd argument must be a numeric string")
	}

	//check if new owner exists
	owner, err := get_owner(stub, owner_id)
	if err != nil {
		fmt.Println("Failed to find owner - " + owner_id)
		return shim.Error(err.Error())
	}

	//check authorizing company (see note in set_owner() about how this is quirky)
	if owner.Company != authed_by_company {
		return shim.Error("The company '" + authed_by_company + "' cannot authorize creation for '" + owner.Company + "'.")
	}

	//check if marble id already exists
	marble, err := get_marble(stub, id)
	if err == nil {
		fmt.Println("This marble already exists - " + id)
		fmt.Println(marble)
		return shim.Error("This marble already exists - " + id) //all stop a marble by this id exists
	}

	//build the marble json string manually
	str := `{
		"docType":"marble",
		"id": "` + id + `",
		"color": "` + color + `",
		"size": ` + strconv.Itoa(size) + `,
		"owner": {
			"id": "` + owner_id + `",
			"username": "` + owner.Username + `",
			"company": "` + owner.Company + `"
		}
	}`
	err = stub.PutState(id, []byte(str)) //store marble with id as key
	if err != nil {
		return shim.Error(err.Error())
	}

	fmt.Println("- end init_marble")
	return shim.Success(nil)
}

// ============================================================================================================================
// Init Owner - create a new owner aka end user, store into chaincode state
//
// Shows off building key's value from GoLang Structure
//
// Inputs - Array of Strings
//           0     ,     1   ,   2
//      owner id   , username, company
// "o9999999999999",     bob", "united marbles"
// ============================================================================================================================
func init_owner(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	var err error
	fmt.Println("starting init_owner")

	if len(args) != 3 {
		return shim.Error("Incorrect number of arguments. Expecting 3")
	}

	//input sanitation
	err = sanitize_arguments(args)
	if err != nil {
		return shim.Error(err.Error())
	}

	var owner Owner
	owner.ObjectType = "marble_owner"
	owner.Id = args[0]
	owner.Username = strings.ToLower(args[1])
	owner.Company = args[2]
	owner.Enabled = true
	fmt.Println(owner)

	//check if user already exists
	_, err = get_owner(stub, owner.Id)
	if err == nil {
		fmt.Println("This owner already exists - " + owner.Id)
		return shim.Error("This owner already exists - " + owner.Id)
	}

	//store user
	ownerAsBytes, _ := json.Marshal(owner)      //convert to array of bytes
	err = stub.PutState(owner.Id, ownerAsBytes) //store owner by its Id
	if err != nil {
		fmt.Println("Could not store user")
		return shim.Error(err.Error())
	}

	fmt.Println("- end init_owner marble")
	return shim.Success(nil)
}

// ============================================================================================================================
// Set Owner on Marble
//
// Shows off GetState() and PutState()
//
// Inputs - Array of Strings
//       0     ,        1      ,        2
//  marble id  ,  to owner id  , company that auth the transfer
// "m999999999", "o99999999999", united_mables"
// ============================================================================================================================
func set_owner(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	var err error
	fmt.Println("starting set_owner")

	// this is quirky
	// todo - get the "company that authed the transfer" from the certificate instead of an argument
	// should be possible since we can now add attributes to the enrollment cert
	// as is.. this is a bit broken (security wise), but it's much much easier to demo! holding off for demos sake

	if len(args) != 3 {
		return shim.Error("Incorrect number of arguments. Expecting 3")
	}

	// input sanitation
	err = sanitize_arguments(args)
	if err != nil {
		return shim.Error(err.Error())
	}

	var marble_id = args[0]
	var new_owner_id = args[1]
	var authed_by_company = args[2]
	fmt.Println(marble_id + "->" + new_owner_id + " - |" + authed_by_company)

	// check if user already exists
	owner, err := get_owner(stub, new_owner_id)
	if err != nil {
		return shim.Error("This owner does not exist - " + new_owner_id)
	}

	// get marble's current state
	marbleAsBytes, err := stub.GetState(marble_id)
	if err != nil {
		return shim.Error("Failed to get marble")
	}
	res := Marble{}
	json.Unmarshal(marbleAsBytes, &res) //un stringify it aka JSON.parse()

	// check authorizing company
	if res.Owner.Company != authed_by_company {
		return shim.Error("The company '" + authed_by_company + "' cannot authorize transfers for '" + res.Owner.Company + "'.")
	}

	// transfer the marble
	res.Owner.Id = new_owner_id //change the owner
	res.Owner.Username = owner.Username
	res.Owner.Company = owner.Company
	jsonAsBytes, _ := json.Marshal(res)       //convert to array of bytes
	err = stub.PutState(args[0], jsonAsBytes) //rewrite the marble with id as key
	if err != nil {
		return shim.Error(err.Error())
	}

	fmt.Println("- end set owner")
	return shim.Success(nil)
}

// ============================================================================================================================
// Disable Marble Owner
//
// Shows off PutState()
//
// Inputs - Array of Strings
//       0     ,        1
//  owner id       , company that auth the transfer
// "o9999999999999", "united_mables"
// ============================================================================================================================
func disable_owner(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	var err error
	fmt.Println("starting disable_owner")

	if len(args) != 2 {
		return shim.Error("Incorrect number of arguments. Expecting 2")
	}

	// input sanitation
	err = sanitize_arguments(args)
	if err != nil {
		return shim.Error(err.Error())
	}

	var owner_id = args[0]
	var authed_by_company = args[1]

	// get the marble owner data
	owner, err := get_owner(stub, owner_id)
	if err != nil {
		return shim.Error("This owner does not exist - " + owner_id)
	}

	// check authorizing company
	if owner.Company != authed_by_company {
		return shim.Error("The company '" + authed_by_company + "' cannot change another companies marble owner")
	}

	// disable the owner
	owner.Enabled = false
	jsonAsBytes, _ := json.Marshal(owner)     //convert to array of bytes
	err = stub.PutState(args[0], jsonAsBytes) //rewrite the owner
	if err != nil {
		return shim.Error(err.Error())
	}

	fmt.Println("- end disable_owner")
	return shim.Success(nil)
}

// Transaction makes payment of X units from A to B
func transferAccounts(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	var A, B string // Entities
	// var Aval, Bval int // Asset holdings
	var Aval, Bval float64 // Asset holdings
	// var X int          // Transaction value
	var X float64 // Transaction value
	var err error

	if len(args) != 3 {
		return shim.Error("Incorrect number of arguments. Expecting 3")
	}

	A = args[0]
	B = args[1]

	// Get the state from the ledger
	// TODO: will be nice to have a GetAllState call to ledger
	Avalbytes, err := stub.GetState(A)
	if err != nil {
		return shim.Error("Failed to get state")
	}
	if Avalbytes == nil {
		return shim.Error("Entity not found")
	}
	// Aval, _ = strconv.Atoi(string(Avalbytes))
	Aval, _ = strconv.ParseFloat(string(Avalbytes), 64)

	Bvalbytes, err := stub.GetState(B)
	if err != nil {
		return shim.Error("Failed to get state")
	}
	if Bvalbytes == nil {
		return shim.Error("Entity not found")
	}
	// Bval, _ = strcoPnv.Atoi(string(Bvalbytes))
	Bval, _ = strconv.ParseFloat(string(Bvalbytes), 64)

	// Perform the execution
	// X, err = strconv.Atoi(args[2])
	X, err = strconv.ParseFloat((args[2]), 64)
	if err != nil {
		return shim.Error("Invalid transaction amount, expecting a integer value")
	}
	Aval = Aval - X
	Bval = Bval + X
	fmt.Printf("Aval = %d, Bval = %d\n", Aval, Bval)

	// Write the state back to the ledger
	// err = stub.PutState(A, []byte(strconv.Itoa(Aval)))
	err = stub.PutState(A, []byte(strconv.FormatFloat(Aval, 'E', -1, 64)))
	if err != nil {
		return shim.Error(err.Error())
	}

	// err = stub.PutState(B, []byte(strconv.Itoa(Bval)))
	err = stub.PutState(B, []byte(strconv.FormatFloat(Bval, 'E', -1, 64)))
	if err != nil {
		return shim.Error(err.Error())
	}

	return shim.Success(nil)
}

func initAccount(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	var err error
	fmt.Println("starting initAccount")
	var A string     // Entities
	var Aval float64 // Asset holdings

	if len(args) != 2 {
		return shim.Error("Incorrect number of arguments. Expecting 2")
	}

	// Initialize the chaincode
	A = args[0]
	// Aval, err = strconv.Atoi(args[1])
	Aval, err = strconv.ParseFloat(args[1], 64)
	if err != nil {
		return shim.Error("Expecting integer value for asset holding")
	}
	fmt.Printf("Aval = %d", Aval)

	// Write the state to the ledger
	// err = stub.PutState(A, []byte(strconv.Itoa(Aval)))
	err = stub.PutState(A, []byte(strconv.FormatFloat(Aval, 'E', -1, 64)))
	if err != nil {
		return shim.Error(err.Error())
	}
	return shim.Success(nil)
}

// Deletes an entity from state
func deleteAccount(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	if len(args) != 1 {
		return shim.Error("Incorrect number of arguments. Expecting 1")
	}

	A := args[0]

	// Delete the key from the state in ledger
	err := stub.DelState(A)
	if err != nil {
		return shim.Error("Failed to delete state")
	}

	return shim.Success(nil)
}
func addTRecord(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	var err error
	var timeStr string
	fmt.Println("starting addTRecord")

	if len(args) != 5 {
		return shim.Error("Incorrect number of arguments. Expecting 5")
	}
	EvId := args[0]
	UserId := args[1]
	CsId := args[2]
	Power, err := strconv.ParseFloat(args[3], 64)
	if err != nil {
		return shim.Error("4rd argument must be a numeric string")
	}
	Price, err := strconv.ParseFloat(args[4], 64)

	if err != nil {
		return shim.Error("5rd argument must be a numeric string")
	}
	timestamp := time.Now().Unix()
	timeStr = TimestampToTime(timestamp, "2006-01-02 15:04:05 PM")
	//build the marble json string manually
	str := `{"UserId": "` + UserId + `","EvId": "` + EvId + `","CsId": "` + CsId + `","Time": "` + timeStr + `","Power": ` + strconv.FormatFloat(Power, 'E', -1, 64) + `,"Price": ` + strconv.FormatFloat(Price, 'E', -1, 64) + `}`
	fmt.Println(str)
	err = stub.PutState(UserId, []byte(str)) //store marble with id as key
	err = stub.PutState(EvId, []byte(str))   //store marble with id as key
	err = stub.PutState(CsId, []byte(str))   //store marble with id as key
	if err != nil {
		return shim.Error(err.Error())
	}

	fmt.Println("- end addTRecord")
	return shim.Success(nil)
}

func addElectricityTradingRecord(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	var err error
	//var StartTime string
	fmt.Println("starting addElectricityTradingRecord")

	if len(args) != 7 {
		return shim.Error("Incorrect number of arguments. Expecting 6")
	}

	OldSoc := args[0]
	AddSoc := args[1]
	Money := args[2]
	BuyerId := args[3]
	SellerId := args[4]
	StartTime := args[5]
	ChargingTime := args[6]

	//timestamp := time.Now().Unix()
	//StartTime = TimestampToTime(timestamp, "2006-01-02 15:04:05 PM")
	//build the marble json string manually
	str := `{"OldSoc": "` + OldSoc + `","AddSoc": "` + AddSoc + `","Money": "` + Money + `","BuyerId": "` + BuyerId + `","SellerId": "` + SellerId + `","ChargingTime": "` + ChargingTime + `","StartTime": "` + StartTime + `"}`
	fmt.Println(str)
	err = stub.PutState(SellerId, []byte(str)) //store marble with id as key
	err = stub.PutState(BuyerId, []byte(str))  //store marble with id as key
	if err != nil {
		return shim.Error(err.Error())
	}

	fmt.Println("- end addElectricityTradingRecord")
	return shim.Success(nil)
}
func addEncryptedOffer(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	var err error
	//var StartTime string
	fmt.Println("starting addEncryptedOffer")

	if len(args) != 5 {
		return shim.Error("Incorrect number of arguments. Expecting 5")
	}
	var encryptedOffer EncryptedOffer
	encryptedOfferList_id := args[0]
	encryptedOffer.Id = args[1]
	encryptedOffer.Number = args[2]
	encryptedOffer.Price = args[3]
	encryptedOffer.Status = args[4]
	//check if new owner exists
	encryptedOfferList, err := get_encryptedOfferList(stub, encryptedOfferList_id)
	//if err != nil {
	//	//fmt.Println("Failed to find encryptedOfferList - " + encryptedOfferList_id)
	//	//return shim.Error(err.Error())
	//} else {
	encryptedOfferList.EncryptedOfferArr = append(encryptedOfferList.EncryptedOfferArr, encryptedOffer)
	fmt.Printf("-encryptedOfferList:\n%s", encryptedOfferList)
	//}
	encryptedOfferList.Id = encryptedOfferList_id
	encryptedOfferList.Flag = "0"
	encryptedOfferList.Time = time.Now().String()
	encryptedOfferListAsBytes, _ := json.Marshal(encryptedOfferList)
	//timestamp := time.Now().Unix()
	//StartTime = TimestampToTime(timestamp, "2006-01-02 15:04:05 PM")
	//build the marble json string manually
	//str := `{"OldSoc": "` + OldSoc + `","AddSoc": "` + AddSoc + `","Money": "` + Money + `","BuyerId": "` + BuyerId + `","SellerId": "` + SellerId + `","ChargingTime": "` + ChargingTime + `","StartTime": "` + StartTime + `"}`
	fmt.Println(encryptedOfferListAsBytes)
	err = stub.PutState(encryptedOfferList_id, encryptedOfferListAsBytes) //store marble with id as key
	if err != nil {
		return shim.Error(err.Error())
	}

	fmt.Println("- end addEncryptedOffer")
	return shim.Success(nil)
}
func addAuthenticOffer(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	var err error
	//var StartTime string
	fmt.Println("starting addAuthenticOffer")

	if len(args) != 6 {
		return shim.Error("Incorrect number of arguments. Expecting 6")
	}
	var authenticOffer AuthenticOffer
	authenticOfferList_id := args[0] + "true"
	authenticOffer.Id = args[1]
	authenticOffer.Number = args[2]
	authenticOffer.Price = args[3]
	authenticOffer.Salt = args[4]
	authenticOffer.Status = args[5]
	//check if new owner exists
	authenticOfferList, err := get_authenticOfferList(stub, authenticOfferList_id)
	//if err != nil {
	//	//fmt.Println("Failed to find encryptedOfferList - " + encryptedOfferList_id)
	//	//return shim.Error(err.Error())
	//} else {
	authenticOfferList.AuthenticOfferrArr = append(authenticOfferList.AuthenticOfferrArr, authenticOffer)
	fmt.Printf("-authenticOfferList:\n%s", authenticOfferList)
	//}
	authenticOfferList.Id = authenticOfferList_id
	authenticOfferList.Flag = "0"
	authenticOfferList.Time = time.Now().String()
	authenticOfferListAsBytes, _ := json.Marshal(authenticOfferList)
	//timestamp := time.Now().Unix()
	//StartTime = TimestampToTime(timestamp, "2006-01-02 15:04:05 PM")
	//build the marble json string manually
	//str := `{"OldSoc": "` + OldSoc + `","AddSoc": "` + AddSoc + `","Money": "` + Money + `","BuyerId": "` + BuyerId + `","SellerId": "` + SellerId + `","ChargingTime": "` + ChargingTime + `","StartTime": "` + StartTime + `"}`
	fmt.Println(authenticOfferListAsBytes)
	err = stub.PutState(authenticOfferList_id, authenticOfferListAsBytes) //store marble with id as key
	if err != nil {
		return shim.Error(err.Error())
	}

	fmt.Println("- end addAuthenticOffer")
	return shim.Success(nil)
}
func verificationOffer(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	var err error
	//var StartTime string
	fmt.Println("starting verificationOffer")

	if len(args) != 1 {
		return shim.Error("Incorrect number of arguments. Expecting 1")
	}
	var qualifiedOffer QualifiedOffer
	var qualifiedOfferList QualifiedOfferList
	encryptedOfferList_id := args[0]
	authenticOfferList_id := args[0] + "true"
	qualifiedOfferList_id := args[0] + "ok"
	encryptedOfferList, err := get_encryptedOfferList(stub, encryptedOfferList_id)
	if err != nil {
		return shim.Error(err.Error())
	}
	authenticOfferList, err := get_authenticOfferList(stub, authenticOfferList_id)
	if err != nil {
		return shim.Error(err.Error())
	}
	fmt.Printf("-encryptedOfferList:\n%s", encryptedOfferList)
	fmt.Printf("-authenticOfferList:\n%s", authenticOfferList)
	for i := 0; i < len(encryptedOfferList.EncryptedOfferArr); i++ {
		for j := 0; j < len(authenticOfferList.AuthenticOfferrArr); j++ {
			if encryptedOfferList.EncryptedOfferArr[i].Id == authenticOfferList.AuthenticOfferrArr[j].Id {
				id := encryptedOfferList.EncryptedOfferArr[i].Id
				status := encryptedOfferList.EncryptedOfferArr[i].Status
				number := encryptedOfferList.EncryptedOfferArr[i].Number
				price := encryptedOfferList.EncryptedOfferArr[i].Price
				aNumber := authenticOfferList.AuthenticOfferrArr[j].Number
				aPrice := authenticOfferList.AuthenticOfferrArr[j].Price
				salt := authenticOfferList.AuthenticOfferrArr[j].Salt
				aN := []byte(aNumber + salt)
				aP := []byte(aPrice + salt)
				aNumberByteArr := md5.Sum(aN)
				aPriceByteArr := md5.Sum(aP)
				aNumberEncrypt := fmt.Sprintf("%x", aNumberByteArr)
				aPriceEncrypt := fmt.Sprintf("%x", aPriceByteArr)
				if number == aNumberEncrypt && price == aPriceEncrypt {
					qualifiedOffer.Id = id
					qualifiedOffer.Price = aPrice
					qualifiedOffer.Number = aNumber
					qualifiedOffer.Status = status
					qualifiedOfferList.QualifiedOfferArr = append(qualifiedOfferList.QualifiedOfferArr, qualifiedOffer)
				}
			}
		}
	}
	qualifiedOfferList.Id = qualifiedOfferList_id
	qualifiedOfferList.Flag = "0"
	qualifiedOfferList.Time = time.Now().String()
	qualifiedOfferAsBytes, _ := json.Marshal(qualifiedOfferList)
	//timestamp := time.Now().Unix()
	//StartTime = TimestampToTime(timestamp, "2006-01-02 15:04:05 PM")
	//build the marble json string manually
	//str := `{"OldSoc": "` + OldSoc + `","AddSoc": "` + AddSoc + `","Money": "` + Money + `","BuyerId": "` + BuyerId + `","SellerId": "` + SellerId + `","ChargingTime": "` + ChargingTime + `","StartTime": "` + StartTime + `"}`
	fmt.Println(qualifiedOfferAsBytes)
	err = stub.PutState(qualifiedOfferList_id, qualifiedOfferAsBytes) //store marble with id as key
	if err != nil {
		return shim.Error(err.Error())
	}
	fmt.Println("- end verificationOffer")
	return shim.Success(nil)
}
