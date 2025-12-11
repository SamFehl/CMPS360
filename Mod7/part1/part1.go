package main

import (
	"bufio"
	"fmt"
	"os"
	"strings"
)

func main() {
	reader := bufio.NewReader(os.Stdin)

	fmt.Print("What is your name? ")
	name, _ := reader.ReadString('\n')
	name = strings.TrimSpace(name)

	fmt.Print("What is your favorite programming language? ")
	lang, _ := reader.ReadString('\n')
	lang = strings.TrimSpace(lang)

	fmt.Print("How many years have you been programming? ")
	var years int
	fmt.Scanln(&years)

	fmt.Println("\n----- Summary -----")
	fmt.Printf("Hello, %s!\n", name)
	fmt.Printf("You like %s and have been programming for %d year(s).\n", lang, years)

	if strings.EqualFold(lang, "Go") || strings.EqualFold(lang, "Golang") {
		fmt.Println("Nice choice! Go is fast, simple, and great for web services.")
	} else {
		fmt.Println("You should try Go too! It’s great for building fast and modern apps.")
	}
}
