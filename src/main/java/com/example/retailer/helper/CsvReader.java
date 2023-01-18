package com.example.retailer.helper;


import com.example.retailer.model.Product;
import com.example.retailer.model.Retailer;
import org.apache.commons.csv.*;
import org.apache.commons.csv.CSVParser;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Service
public class CsvReader {



    public void readRetailerCsv( ArrayList<Retailer> retailerList) throws IOException {
        try (
                Reader reader = Files.newBufferedReader(Paths.get("/Users/ruthsan/Downloads/springDemo 2/csvfiles/retailer.csv"));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
        ){
                for (CSVRecord csvRecord : csvParser) {
                    int id = Integer.parseInt(csvRecord.get(0));
                    String name = csvRecord.get(1);
                    HashMap<Integer,ArrayList<Product> > retailersProductList=new HashMap<Integer,ArrayList<Product> >();
                    Retailer retailer = new Retailer(id, name, retailersProductList );
                    retailerList.add(retailer);
                }

        }

    }
}
