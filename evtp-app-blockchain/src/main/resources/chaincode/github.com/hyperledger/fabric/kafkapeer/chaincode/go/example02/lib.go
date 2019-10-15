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
	"encoding/json"
	"errors"
	"fmt"
	"strconv"

	"github.com/hyperledger/fabric/core/chaincode/shim"
)

// ============================================================================================================================
// Get Marble - get a marble asset from ledger
// ============================================================================================================================
func get_marble(stub shim.ChaincodeStubInterface, id string) (Marble, error) {
	var marble Marble
	marbleAsBytes, err := stub.GetState(id) //getState retreives a key/value from the ledger
	if err != nil {                         //this seems to always succeed, even if key didn't exist
		return marble, errors.New("Failed to find marble - " + id)
	}
	json.Unmarshal(marbleAsBytes, &marble) //un stringify it aka JSON.parse()

	if marble.Id != id { //test if marble is actually here or just nil
		return marble, errors.New("Marble does not exist - " + id)
	}

	return marble, nil
}

// ============================================================================================================================
// Get Owner - get the owner asset from ledger
// ============================================================================================================================
func get_owner(stub shim.ChaincodeStubInterface, id string) (Owner, error) {
	var owner Owner
	ownerAsBytes, err := stub.GetState(id) //getState retreives a key/value from the ledger
	if err != nil {                        //this seems to always succeed, even if key didn't exist
		return owner, errors.New("Failed to get owner - " + id)
	}
	json.Unmarshal(ownerAsBytes, &owner) //un stringify it aka JSON.parse()

	if len(owner.Username) == 0 { //test if owner is actually here or just nil
		return owner, errors.New("Owner does not exist - " + id + ", '" + owner.Username + "' '" + owner.Company + "'")
	}

	return owner, nil
}

// ========================================================
// Input Sanitation - dumb input checking, look for empty strings
// ========================================================
func sanitize_arguments(strs []string) error {
	for i, val := range strs {
		if len(val) <= 0 {
			return errors.New("Argument " + strconv.Itoa(i) + " must be a non-empty string")
		}
		if len(val) > 32 {
			return errors.New("Argument " + strconv.Itoa(i) + " must be <= 32 characters")
		}
	}
	return nil
}
func get_encryptedOfferList(stub shim.ChaincodeStubInterface, id string) (EncryptedOfferList, error) {
	var encryptedOfferList EncryptedOfferList
	encryptedOfferListAsBytes, err := stub.GetState(id) //getState retreives a key/value from the ledger
	if err != nil {                                     //this seems to always succeed, even if key didn't exist
		return encryptedOfferList, errors.New("Failed to get encryptedOfferList - " + id)
	}
	json.Unmarshal(encryptedOfferListAsBytes, &encryptedOfferList) //un stringify it aka JSON.parse()
	fmt.Printf("-encryptedOfferList:\n%s", encryptedOfferList)

	if len(encryptedOfferList.EncryptedOfferArr) == 0 { //test if owner is actually here or just nil
		return encryptedOfferList, errors.New("encryptedOfferList does not exist - " + id)
	}

	return encryptedOfferList, nil
}
func get_authenticOfferList(stub shim.ChaincodeStubInterface, id string) (AuthenticOfferList, error) {
	var authenticOfferList AuthenticOfferList
	authenticOfferListAsBytes, err := stub.GetState(id) //getState retreives a key/value from the ledger
	if err != nil {                                     //this seems to always succeed, even if key didn't exist
		return authenticOfferList, errors.New("Failed to get authenticOfferList - " + id)
	}
	json.Unmarshal(authenticOfferListAsBytes, &authenticOfferList) //un stringify it aka JSON.parse()
	fmt.Printf("-authenticOfferList:\n%s", authenticOfferList)
	if len(authenticOfferList.AuthenticOfferrArr) == 0 { //test if owner is actually here or just nil
		return authenticOfferList, errors.New("authenticOfferList does not exist - " + id)
	}

	return authenticOfferList, nil
}
func get_qualifiedOfferList(stub shim.ChaincodeStubInterface, id string) (QualifiedOfferList, error) {
	var qualifiedOfferList QualifiedOfferList
	qualifiedOfferListAsBytes, err := stub.GetState(id) //getState retreives a key/value from the ledger
	if err != nil {                                     //this seems to always succeed, even if key didn't exist
		return qualifiedOfferList, errors.New("Failed to get qualifiedOfferList - " + id)
	}
	json.Unmarshal(qualifiedOfferListAsBytes, &qualifiedOfferList) //un stringify it aka JSON.parse()
	fmt.Printf("-qualifiedOfferList:\n%s", qualifiedOfferList)

	if len(qualifiedOfferList.QualifiedOfferArr) == 0 { //test if owner is actually here or just nil
		return qualifiedOfferList, errors.New("qualifiedOfferList does not exist - " + id)
	}

	return qualifiedOfferList, nil
}
func byteString(p []byte) string {
	for i := 0; i < len(p); i++ {
		if p[i] == 0 {
			return string(p[0:i])
		}
	}
	return string(p)
}
