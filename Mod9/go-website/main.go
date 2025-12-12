package main

import (
	"html/template"
	"log"
	"net/http"
)

// helper to render a template file with optional data
func renderTemplate(w http.ResponseWriter, tmpl string, data interface{}) {
	t, err := template.ParseFiles("templates/" + tmpl)
	if err != nil {
		log.Printf("Error parsing template %s: %v", tmpl, err)
		http.Error(w, "Error loading page", http.StatusInternalServerError)
		return
	}

	if err := t.Execute(w, data); err != nil {
		log.Printf("Error executing template %s: %v", tmpl, err)
		http.Error(w, "Error rendering page", http.StatusInternalServerError)
		return
	}
}

func homeHandler(w http.ResponseWriter, r *http.Request) {
	renderTemplate(w, "index.html", nil)
}

func aboutHandler(w http.ResponseWriter, r *http.Request) {
	renderTemplate(w, "about.html", nil)
}

func hobbyHandler(w http.ResponseWriter, r *http.Request) {
	renderTemplate(w, "hobby.html", nil)
}

func main() {
	// Routes for pages
	http.HandleFunc("/", homeHandler)
	http.HandleFunc("/about", aboutHandler)
	http.HandleFunc("/hobby", hobbyHandler)

	// Serve static files: CSS, images, etc.
	fs := http.FileServer(http.Dir("static"))
	http.Handle("/static/", http.StripPrefix("/static/", fs))

	log.Println("Server is running on http://localhost:8080")
	log.Fatal(http.ListenAndServe(":8080", nil))
}
