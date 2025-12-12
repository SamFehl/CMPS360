package main

import (
	"html/template"
	"log"
	"net/http"
)

// Handler function for the homepage
func homeHandler(w http.ResponseWriter, r *http.Request) {
	tmpl, err := template.ParseFiles("templates/index.html")
	if err != nil {
		http.Error(w, "Error loading template", http.StatusInternalServerError)
		return
	}
	tmpl.Execute(w, nil)
}

func aboutHandler(w http.ResponseWriter, r *http.Request) {
	tmpl, err := template.ParseFiles("templates/about.html")
	if err != nil {
		http.Error(w, "Error loading template", http.StatusInternalServerError)
		return
	}
	tmpl.Execute(w, nil)
}

func main() {
	http.HandleFunc("/", homeHandler)                                                          //Route for the homepage
	http.HandleFunc("/about", aboutHandler)                                                    // About page
	http.Handle("/static/", http.StripPrefix("/static/", http.FileServer(http.Dir("static")))) //Serve static files

	log.Println("Server is running on http://localhost:8080")
	http.ListenAndServe(":8080", nil) //Start the server on port 8080
}
