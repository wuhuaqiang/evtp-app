package dao

import . "github.com/utils"
import . "github.com/pojo"
import "io/ioutil"

type UserDao struct {
	Insert     func(user User) (int64, error)
	SelectById func(id int64) (User, error) `mapperParams:"id"`
}

var userDao = UserDao{}

func init() {
	conn := DBConnUtil{}.GetConn()
	bytes, _ := ioutil.ReadFile("UserDao.xml")
	conn.Engine.WriteMapperPtr(&userDao, bytes)
}
