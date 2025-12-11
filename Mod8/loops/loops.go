package main

import "fmt"

func main() {
	i := 99

	for {
		fmt.Println(i)
		i += 1
		break
	}

	i = 5
	for i < 10 {
		fmt.Println(i)
		i++
	}
}
