package main

import (
	"database/sql"
	"fmt"
	_ "github.com/go-sql-driver/mysql"
)

type DbWorker struct {
	Dsn      string
	Db       *sql.DB
	UserInfo userTB
}
type userTB struct {
	Id       sql.NullString
	UserName sql.NullString
	Password sql.NullString
}

func main() {
	var err error
	dbw := DbWorker{
		Dsn: "root:123456@tcp(10.168.1.245:3306)/springcloud-app?charset=utf8mb4",
	}
	dbw.Db, err = sql.Open("mysql", dbw.Dsn)
	if err != nil {
		panic(err)
		return
	}
	defer dbw.Db.Close()

	//dbw.insertData()
	dbw.queryData()
}

func (dbw *DbWorker) insertData() {
	stmt, _ := dbw.Db.Prepare(`INSERT INTO user (name, age) VALUES (?, ?)`)
	defer stmt.Close()

	ret, err := stmt.Exec("xys", 23)
	if err != nil {
		fmt.Printf("insert data error: %v\n", err)
		return
	}
	if LastInsertId, err := ret.LastInsertId(); nil == err {
		fmt.Println("LastInsertId:", LastInsertId)
	}
	if RowsAffected, err := ret.RowsAffected(); nil == err {
		fmt.Println("RowsAffected:", RowsAffected)
	}
}

func (dbw *DbWorker) QueryDataPre() {
	dbw.UserInfo = userTB{}
}
func (dbw *DbWorker) queryData() {
	stmt, _ := dbw.Db.Prepare(`SELECT * From user`)
	defer stmt.Close()

	dbw.QueryDataPre()

	rows, err := stmt.Query()
	defer rows.Close()
	if err != nil {
		fmt.Printf("insert data error: %v\n", err)
		return
	}
	for rows.Next() {
		rows.Scan(&dbw.UserInfo.Id, &dbw.UserInfo.UserName, &dbw.UserInfo.Password)
		if err != nil {
			fmt.Printf(err.Error())
			continue
		}
		if !dbw.UserInfo.UserName.Valid {
			dbw.UserInfo.UserName.String = ""
		}
		if !dbw.UserInfo.Password.Valid {
			dbw.UserInfo.Password.String = ""
		}
		fmt.Println("get data, id: ", dbw.UserInfo.Id, " UserName: ", dbw.UserInfo.UserName.String, " Password: ", dbw.UserInfo.Password.String)
	}

	err = rows.Err()
	if err != nil {
		fmt.Printf(err.Error())
	}
}
