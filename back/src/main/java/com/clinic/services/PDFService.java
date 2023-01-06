package com.clinic.services;

import com.clinic.entities.Order;
import com.itextpdf.text.DocumentException;

import java.io.IOException;

public interface PDFService {

    public String generateCommercial(Order order) throws IOException, DocumentException;

    public String generateRiskList(Order order) throws IOException, DocumentException;
}
