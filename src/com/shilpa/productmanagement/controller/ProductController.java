
package com.shilpa.productmanagement.controller;

import com.shilpa.productmanagement.dao.ProductDAO;
import com.shilpa.productmanagement.entity.Product;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class ProductController {
    Scanner input;
    ProductDAO productDAO;

    public ProductController(Scanner input, ProductDAO productDAO) {
        this.input = input;
        this.productDAO = productDAO;
    }
    
    private void menu()
    {
            System.out.println("Enter your choice: ");
            System.out.println("1. Add Product");
            System.out.println("2. Delete Product");
            System.out.println("3. Search by Id");
            System.out.println("4. Display all");
            System.out.println("5. Write to file");
            System.out.println("6. Exit");
            
    }
    
    public void process() throws IOException
    {
        while(true)
        {
            menu();
            System.out.print("Enter your choice[1-6]: ");
            switch(input.nextInt())
            {
                case 1:
                    while(true){
                        add();
                         System.out.print("Do you want to add more?(y/n):");
                        if(input.next().equalsIgnoreCase("n"))
                        {
                            break;
                        }
                    }
                break;
                case 2:
                     delete();
                break;
                case 3:
                    search();
                break;
                case 4:
                    displayAll();
                break;
                case 5:
                    writeToFile();
                    break;
                case 6:
                    System.out.println("Do you really want to exit?(y/n)");
                    if(input.next().equalsIgnoreCase("y"))
                    {
                        System.exit(0);
                    }
                    break;
                   
                   
              
        }
        }
    }
    
    private void add(){
    Product p=new Product();
    System.out.print("Enter product id: ");
    p.setId(input.nextInt());
    System.out.print("Enter product name: ");
    p.setName(input.next());
    System.out.print("Enter product price: ");
    p.setPrice(input.nextDouble());
    System.out.print("Enter product quantity: ");
   p.setQuantity(input.nextInt());
    System.out.print("Enter product status ");
   p.setStatus(input.nextBoolean());
                        
    productDAO.insert(p);
                       
    }
           
    private void delete()
    {
       System.out.print("Enter product id to be deleted: ");
       if(productDAO.delete(input.nextInt()))
       {
           System.out.println("Deleted successfully!!");
       }else
            System.out.println("Cannot delete the product!");
                    
    }
    
    private void search()
    {
        System.out.print("Enter product id to be searched: ");
        Product p = productDAO.getById(input.nextInt());
        if(p!=null){
        System.out.println("Product details: ");
        System.out.println("Product id: "+p.getId());
        System.out.println("Product name: "+p.getName());
        System.out.println("Product price: "+p.getPrice());
        System.out.println("Product quantity: "+p.getQuantity());
        System.out.println("Product status: "+p.isStatus());
        }else
            System.out.println("Cannot find the product.");
    }
    
    private void displayAll()
    {
        System.out.println("Product list: ");
        System.out.println("*********************************");
        productDAO.getAll().forEach(p-> {
        System.out.println("Product id: "+p.getId());
        System.out.println("Product name: "+p.getName());
        System.out.println("Product price: "+p.getPrice());
        System.out.println("Product quantity: "+p.getQuantity());
        System.out.println("Product status: "+p.isStatus());
        System.out.println("");
        });
        
        
    }
    
    private void writeToFile() throws IOException
    {
        
        
            
        try (FileWriter writer = new FileWriter("C:\\Users\\User\\Documents\\NetBeansProjects\\products\\products.txt");
                BufferedWriter bufferedwriter =new BufferedWriter(writer)
                ) {
            productDAO.getAll().forEach((Product p)->{
                try {
                    
                    bufferedwriter.write(p.getId()+" "+p.getName()+" "+p.getPrice()+" " +p.getQuantity()+" "+p.isStatus());
                    bufferedwriter.newLine();
                    
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            });
            System.out.println("Write Successful.");
            bufferedwriter.close();
        }
                    
                
}
}
