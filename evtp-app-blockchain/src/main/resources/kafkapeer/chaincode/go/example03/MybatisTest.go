package main

import (
	. "github.com/pojo"
	. "github.com/dao"
	"fmt"
	"testing"
)

func Test_insert(t *testing.T) {
	//bytes, _ := ioutil.ReadFile("src/UserDao.xml")
	//fmt.Println(bytes)
	user := User{UserName: "Victor", PassWord: "1"}

	var result, err = GetUserDao().Insert(user)
	if err != nil {
		panic(err)
	}
	fmt.Println("result=", result)
	fmt.Println("id=", user.Id)
}

func Test_selectById(t *testing.T) {
	var result, err = GetUserDao().SelectById(2)
	if err != nil {
		panic(err)
	}
	fmt.Println("user=", result)
}
