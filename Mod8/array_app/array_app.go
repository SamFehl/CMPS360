package main

import "fmt"

func main() {
	const conferenceTickets int = 50
	var remainingTickets uint = 50
	conferenceName := "Bison Up Conference"
	bookings := []string{}

	fmt.Printf(`Welcome to %v booking application.
	We have a total of %v tickets still available.
	Get your tickets here to attend.
	`, conferenceName, remainingTickets)

	//Declare Data Types
	var firstName string
	var lastName string
	var email string
	var userTickets uint

	//collect the data
	fmt.Println("Enter your First Name: ")
	fmt.Scanln(&firstName)

	fmt.Println("Enter your Last Name: ")
	fmt.Scanln(&lastName)

	fmt.Println("Enter your email: ")
	fmt.Scanln(&email)

	fmt.Println("Enter number of Tickets: ")
	fmt.Scanln(&userTickets)

	//Logic for Booking System
	remainingTickets = remainingTickets - userTickets
	bookings = append(bookings, firstName+" "+lastName)

	//Output
	fmt.Printf("Thanks %v %v for booking %v tickets. You will receive a confirmation email at %v \n",
		firstName, lastName, userTickets, email)

	fmt.Printf("%v tickets remaing for %v\n", remainingTickets, conferenceName)
	fmt.Printf("These are all of our bookings: %v\n", bookings)

}
