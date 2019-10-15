package pojo

type User struct {
	Id       int64  `json:"id"`
	UserName string `json:"username"`
	PassWord string    `json:"password"`
}
