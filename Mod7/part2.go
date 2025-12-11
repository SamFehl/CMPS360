package main

import (
	"fmt"
	"log"
	"net/http"
	"os"
)

// readFile reads data.txt and returns its content.
func readFile() (string, error) {
	bytes, err := os.ReadFile("data.txt")
	if err != nil {
		return "", err
	}
	return string(bytes), nil
}

func homeHandler(w http.ResponseWriter, r *http.Request) {
	fmt.Fprintln(w, "Welcome to the Go Web Service!")
	fmt.Fprintln(w, "Go to /data to see the contents of the file.")
}

func dataHandler(w http.ResponseWriter, r *http.Request) {
	content, err := readFile()
	if err != nil {
		http.Error(w, "Could not read file: "+err.Error(), http.StatusInternalServerError)
		return
	}

	fmt.Fprintln(w, "Contents of data.txt:")
	fmt.Fprintln(w, "----------------------")
	fmt.Fprintln(w, content)
}

func main() {
	http.HandleFunc("/", homeHandler)
	http.HandleFunc("/data", dataHandler)

	fmt.Println("Server running at http://localhost:8080")
	log.Fatal(http.ListenAndServe(":8080", nil))
}
