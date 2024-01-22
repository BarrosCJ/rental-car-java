package model.services;

import java.time.Duration;

import model.entities.CarRental;
import model.entities.InVoice;
import model.services.BrazilTaxService;

public class RentalService {

	private double pricePerHour;
	private double pricePerDay;
	
	private TaxService taxService;

	public RentalService(double pricePerHour, double pricePerDay, TaxService taxService) {
		this.pricePerHour = pricePerHour;
		this.pricePerDay = pricePerDay;
		this.taxService = taxService;
	}
	
	public void processInvoice(CarRental carRental) {
		
		double minutes = Duration.between(carRental.getStart(), carRental.getFinish()).toMinutes();
		double hours = minutes / 60.0;
		
		double basicPayment;
		if (hours <= 12.0) {
			basicPayment = pricePerHour * Math.ceil(hours);
		}
		else {
			basicPayment = pricePerDay * Math.ceil(hours / 24.0);
		}
		
		double tax = taxService.tax(basicPayment);
		
		carRental.setInvoice(new InVoice(basicPayment, tax));
	}
	
}
