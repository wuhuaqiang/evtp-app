package utils

import (
	_ "github.com/go-sql-driver/mysql"
	"github.com/zhuxiujia/GoMybatis"
)

type DBConnUtil struct {
	Engine GoMybatis.GoMybatisEngine
}

func (conn DBConnUtil) GetConn() DBConnUtil {
	conn.Engine = GoMybatis.GoMybatisEngine{}.New()
	err, _ := conn.Engine.Open("mysql", "root:123456@tcp(10.168.1.245:3306)/springcloud-app?charset=utf8mb4") //此处请按格式填写你的mysql链接，这里用*号代替
	if err != nil {
		panic(err.Error())
	}
	conn.Engine.SetLogEnable(true)
	//conn.Engine.SetLog(&GoMybatis.LogStandard{
	//  PrintlnFunc: func(messages []byte) {
	//    fmt.Printf(messages)
	//  },
	//})
	return conn
}
