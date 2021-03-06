package com.itgenius.converter;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.itgenius.model.Product;

public class ProductConverter {
	
	// Convert Product Object to MongoDB Document
	public static Document toDocument(Product p) {
		Document doc = new Document("name",p.getName()).append("price", p.getPrice());
		if(p.getId() != null) {
			doc.append("_id", new ObjectId(p.getId()));
		}
		return doc;
	}
	
	// Convert MongoDB Document to Product
	public static Product toProduct(Document doc) {
		Product p = new Product();
		p.setName((String) doc.get("name"));
		p.setPrice((double) doc.get("price"));
		p.setId(((ObjectId) doc.get("_id")).toString());
		return p;
	}

}
