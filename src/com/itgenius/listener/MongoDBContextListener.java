package com.itgenius.listener;

import java.util.Arrays;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.MongoException;
import com.mongodb.ServerAddress;

@WebListener
public class MongoDBContextListener implements ServletContextListener {
	
	@Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
        	
        	ServletContext ctx = sce.getServletContext();
        	
            MongoCredential credential = MongoCredential.createCredential(
            		ctx.getInitParameter("MONGODB_USERNAME"), 
            		ctx.getInitParameter("MONGODB_AUTHENTICATION_DATABASE"),
            		ctx.getInitParameter("MONGODB_PASSWORD").toCharArray()
            );
            
            
            MongoClient mongo =  new MongoClient(
            		new ServerAddress(
            					ctx.getInitParameter("MONGODB_HOST"), 
            					Integer.parseInt(ctx.getInitParameter("MONGODB_PORT")
            				)
            			), Arrays.asList(credential)
            );
        
            System.out.println("MongoClient initialized successfully");
            sce.getServletContext().setAttribute("MONGO_CLIENT", mongo);
            
        } catch (MongoException e) {
            throw new RuntimeException("MongoClient init failed");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        MongoClient mongo = (MongoClient) sce.getServletContext()
                            .getAttribute("MONGO_CLIENT");
        mongo.close();
        System.out.println("MongoClient closed successfully");
    }
    
}
