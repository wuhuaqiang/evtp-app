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
	"bytes"
	"encoding/json"
	"fmt"
	"github.com/hyperledger/fabric/core/chaincode/shim"
	pb "github.com/hyperledger/fabric/protos/peer"
)

// ============================================================================================================================
// Read - read a generic variable from ledger
//
// Shows Off GetState() - reading a key/value from the ledger
//
// Inputs - Array of strings
//  0
//  key
//  "abc"
// 
// Returns - string
// ============================================================================================================================
func read(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	var key, jsonResp string
	var err error
	fmt.Println("starting read")

	if len(args) != 1 {
		return shim.Error("Incorrect number of arguments. Expecting key of the var to query")
	}

	// input sanitation
	err = sanitize_arguments(args)
	if err != nil {
		return shim.Error(err.Error())
	}

	key = args[0]
	valAsbytes, err := stub.GetState(key) //get the var from ledger
	if err != nil {
		jsonResp = "{\"Error\":\"Failed to get state for " + key + "\"}"
		return shim.Error(jsonResp)
	}

	fmt.Println("- end read")
	return shim.Success(valAsbytes) //send it onward
}

// ============================================================================================================================
// Get everything we need (owners + marbles + companies)
//
// Inputs - none
//
// Returns:
// {
//	"owners": [{
//			"id": "o99999999",
//			"company": "United Marbles"
//			"username": "alice"
//	}],
//	"marbles": [{
//		"id": "m1490898165086",
//		"color": "white",
//		"docType" :"marble",
//		"owner": {
//			"company": "United Marbles"
//			"username": "alice"
//		},
//		"size" : 35
//	}]
// }
// ============================================================================================================================
func read_everything(stub shim.ChaincodeStubInterface) pb.Response {
	type Everything struct {
		Owners  []Owner  `json:"owners"`
		Marbles []Marble `json:"marbles"`
	}
	var everything Everything

	// ---- Get All Marbles ---- //
	resultsIterator, err := stub.GetStateByRange("m0", "m9999999999999999999")
	if err != nil {
		return shim.Error(err.Error())
	}
	defer resultsIterator.Close()

	for resultsIterator.HasNext() {
		aKeyValue, err := resultsIterator.Next()
		if err != nil {
			return shim.Error(err.Error())
		}
		queryKeyAsStr := aKeyValue.Key
		queryValAsBytes := aKeyValue.Value
		fmt.Println("on marble id - ", queryKeyAsStr)
		var marble Marble
		json.Unmarshal(queryValAsBytes, &marble)                //un stringify it aka JSON.parse()
		everything.Marbles = append(everything.Marbles, marble) //add this marble to the list
	}
	fmt.Println("marble array - ", everything.Marbles)

	// ---- Get All Owners ---- //
	ownersIterator, err := stub.GetStateByRange("o0", "o9999999999999999999")
	if err != nil {
		return shim.Error(err.Error())
	}
	defer ownersIterator.Close()

	for ownersIterator.HasNext() {
		aKeyValue, err := ownersIterator.Next()
		if err != nil {
			return shim.Error(err.Error())
		}
		queryKeyAsStr := aKeyValue.Key
		queryValAsBytes := aKeyValue.Value
		fmt.Println("on owner id - ", queryKeyAsStr)
		var owner Owner
		json.Unmarshal(queryValAsBytes, &owner) //un stringify it aka JSON.parse()

		if owner.Enabled { //only return enabled owners
			everything.Owners = append(everything.Owners, owner) //add this marble to the list
		}
	}
	fmt.Println("owner array - ", everything.Owners)

	//change to array of bytes
	everythingAsBytes, _ := json.Marshal(everything) //convert to array of bytes
	return shim.Success(everythingAsBytes)
}

// ============================================================================================================================
// Get history of asset
//
// Shows Off GetHistoryForKey() - reading complete history of a key/value
//
// Inputs - Array of strings
//  0
//  id
//  "m01490985296352SjAyM"
// ============================================================================================================================
func getHistory(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	type AuditHistory struct {
		TxId  string `json:"txId"`
		Value Marble `json:"value"`
	}
	var history []AuditHistory;
	var marble Marble

	if len(args) != 1 {
		return shim.Error("Incorrect number of arguments. Expecting 1")
	}

	marbleId := args[0]
	fmt.Printf("- start getHistoryForMarble: %s\n", marbleId)

	// Get History
	resultsIterator, err := stub.GetHistoryForKey(marbleId)
	if err != nil {
		return shim.Error(err.Error())
	}
	defer resultsIterator.Close()

	for resultsIterator.HasNext() {
		historyData, err := resultsIterator.Next()
		if err != nil {
			return shim.Error(err.Error())
		}

		var tx AuditHistory
		tx.TxId = historyData.TxId                 //copy transaction id over
		json.Unmarshal(historyData.Value, &marble) //un stringify it aka JSON.parse()
		if historyData.Value == nil { //marble has been deleted
			var emptyMarble Marble
			tx.Value = emptyMarble //copy nil marble
		} else {
			json.Unmarshal(historyData.Value, &marble) //un stringify it aka JSON.parse()
			tx.Value = marble                          //copy marble over
		}
		history = append(history, tx) //add this tx to the list
	}
	fmt.Printf("- getHistoryForMarble returning:\n%s", history)

	//change to array of bytes
	historyAsBytes, _ := json.Marshal(history) //convert to array of bytes
	return shim.Success(historyAsBytes)
}

// ============================================================================================================================
// Get history of asset - performs a range query based on the start and end keys provided.
//
// Shows Off GetStateByRange() - reading a multiple key/values from the ledger
//
// Inputs - Array of strings
//       0     ,    1
//   startKey  ,  endKey
//  "marbles1" , "marbles5"
// ============================================================================================================================
func getMarblesByRange(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	if len(args) != 2 {
		return shim.Error("Incorrect number of arguments. Expecting 2")
	}

	startKey := args[0]
	endKey := args[1]

	resultsIterator, err := stub.GetStateByRange(startKey, endKey)
	if err != nil {
		return shim.Error(err.Error())
	}
	defer resultsIterator.Close()

	// buffer is a JSON array containing QueryResults
	var buffer bytes.Buffer
	buffer.WriteString("[")

	bArrayMemberAlreadyWritten := false
	for resultsIterator.HasNext() {
		aKeyValue, err := resultsIterator.Next()
		if err != nil {
			return shim.Error(err.Error())
		}
		queryResultKey := aKeyValue.Key
		queryResultValue := aKeyValue.Value

		// Add a comma before array members, suppress it for the first array member
		if bArrayMemberAlreadyWritten == true {
			buffer.WriteString(",")
		}
		buffer.WriteString("{\"Key\":")
		buffer.WriteString("\"")
		buffer.WriteString(queryResultKey)
		buffer.WriteString("\"")

		buffer.WriteString(", \"Record\":")
		// Record is a JSON object, so we write as-is
		buffer.WriteString(string(queryResultValue))
		buffer.WriteString("}")
		bArrayMemberAlreadyWritten = true
	}
	buffer.WriteString("]")

	fmt.Printf("- getMarblesByRange queryResult:\n%s\n", buffer.String())

	return shim.Success(buffer.Bytes())
}

// query callback representing the query of a chaincode
func queryAccount(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	var A string // Entities
	var err error

	if len(args) != 1 {
		return shim.Error("Incorrect number of arguments. Expecting name of the person to query")
	}

	A = args[0]

	// Get the state from the ledger
	Avalbytes, err := stub.GetState(A)
	if err != nil {
		jsonResp := "{\"Error\":\"Failed to get state for " + A + "\"}"
		return shim.Error(jsonResp)
	}

	if Avalbytes == nil {
		jsonResp := "{\"Error\":\"Nil amount for " + A + "\"}"
		return shim.Error(jsonResp)
	}

	jsonResp := "{\"Name\":\"" + A + "\",\"Amount\":\"" + string(Avalbytes) + "\"}"
	fmt.Printf("Query Response:%s\n", jsonResp)
	return shim.Success(Avalbytes)
}
func getAccountHistory(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	type AuditHistory struct {
		TxId  string  `json:"txId"`
		Value Account `json:"value"`
	}
	var history []AuditHistory;
	var account Account
	var accountValue float64

	if len(args) != 1 {
		return shim.Error("Incorrect number of arguments. Expecting 1")
	}

	accountId := args[0]
	fmt.Printf("- start getAccountHistory: %s\n", accountId)

	// Get History
	resultsIterator, err := stub.GetHistoryForKey(accountId)
	if err != nil {
		return shim.Error(err.Error())
	}
	defer resultsIterator.Close()

	for resultsIterator.HasNext() {
		historyData, err := resultsIterator.Next()
		if err != nil {
			return shim.Error(err.Error())
		}

		var tx AuditHistory
		tx.TxId = historyData.TxId //copy transaction id over
		fmt.Printf("-historyData:\n%s", historyData)
		fmt.Printf("-TxId:\n%s", historyData.TxId)
		json.Unmarshal(historyData.Value, &account) //un stringify it aka JSON.parse()
		fmt.Printf("-Value:\n%s", historyData.Value)
		if historyData.Value == nil { //marble has been deleted
			var emptyAccount Account
			tx.Value = emptyAccount //copy nil marble
		} else {
			json.Unmarshal(historyData.Value, &accountValue)
			account.Id = accountId
			account.Value = accountValue
			//un stringify it aka JSON.parse()
			tx.Value = account //copy marble over
			fmt.Printf("-else--Account:\n%s", account)
		}
		history = append(history, tx) //add this tx to the list
	}
	fmt.Printf("- getHistoryForMarble returning:\n%s", history)

	//change to array of bytes
	historyAsBytes, _ := json.Marshal(history) //convert to array of bytes
	return shim.Success(historyAsBytes)
}
func getTRecordHistory(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	type TRecordHistory struct {
		TxId  string  `json:"txId"`
		Value TRecord `json:"value"`
	}
	var history []TRecordHistory
	var tRecord TRecord
	var tRecordStr string

	if len(args) != 1 {
		return shim.Error("Incorrect number of arguments. Expecting 1")
	}

	Id := args[0]
	fmt.Printf("- start getAccountHistory: %s\n", Id)

	// Get History
	resultsIterator, err := stub.GetHistoryForKey(Id)
	if err != nil {
		return shim.Error(err.Error())
	}
	defer resultsIterator.Close()

	for resultsIterator.HasNext() {
		historyData, err := resultsIterator.Next()
		if err != nil {
			return shim.Error(err.Error())
		}
		type Person struct {
			Name   string
			Age    int
			Gender bool
		}
		//unmarshal to struct
		var p Person
		var str = `{"Name":"junbin", "Age":21, "Gender":true}`
		fmt.Println("********************************************************")
		fmt.Println(str)
		fmt.Printf("jsonStr:\n%s", str)
		json.Unmarshal([]byte(str), &p)
		//result --> junbin : 21 : true
		fmt.Printf("PersonStr:\n%s", p)
		var tx TRecordHistory
		tx.TxId = historyData.TxId //copy transaction id over
		fmt.Printf("-historyData:\n%s", historyData)
		fmt.Printf("-TxId:\n%s", historyData.TxId)
		json.Unmarshal(historyData.Value, &tRecord) //un stringify it aka JSON.parse()
		fmt.Printf("-Value:\n%s", historyData.Value)
		fmt.Printf("-tRecord:\n%s", tRecord)
		if historyData.Value == nil { //marble has been deleted
			var emptyTRecord TRecord
			tx.Value = emptyTRecord //copy nil marble
		} else {
			tRecordStr = string(historyData.Value)
			fmt.Println(tRecordStr)
			fmt.Printf("-tRecordStr:\n%s", tRecordStr)
			//type JiaoYi struct {
			//	CsId    string
			//	EvId    string
			//	UserId  string
			//	TimeStr string
			//	Power   float64
			//	Price   float64
			//}
			//var jy JiaoYi
			//var str = `{"CsId":"123456", "EvId":"abc", "UserId":"whq","Price":1000.245,"Power":10.45,"TimeStr":"2019-04-21"}`
			//json.Unmarshal([]byte(str), &jy)
			//fmt.Printf("jyEg:\n%s", jy)
			//fmt.Println(jy.CsId, ":", jy.EvId, ":", jy.UserId, ":", jy.Power, ":", jy.Price)
			//un stringify it aka JSON.parse()
			json.Unmarshal([]byte(historyData.Value), &tRecord)
			fmt.Printf("-tRecordObject:\n%s", tRecord)
			fmt.Println(tRecord.CsId, ":", tRecord.EvId, ":", tRecord.UserId, ":", tRecord.Power, ":", tRecord.Price, ":", tRecord.Time)
			tx.Value = tRecord //copy marble over
			fmt.Printf("-else--Account:\n%s", tRecord)
		}
		history = append(history, tx) //add this tx to the list
		fmt.Println("********************************************************")
	}
	fmt.Printf("- getHistoryForMarble returning:\n%s", history)

	//change to array of bytes
	historyAsBytes, _ := json.Marshal(history) //convert to array of bytes
	return shim.Success(historyAsBytes)
}
