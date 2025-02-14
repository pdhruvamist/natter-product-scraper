# Welcome!
This is a command line application that scrapes an e-commerce webpage and returns a JSON array of all the products on the page.

The link to the page to scrape is https://develop.dkaylp6d3ryw1.amplifyapp.com/

It follows each available product links. When the product has multiple HDD options, return each option as a separate product.
When the product has multiple colors, return the color options as a string array. For each product, return the name, description and price.
Additionally, there should be a total field which is the sum of all products on the page.


And the expected response is:
```json
{
  "results": [
    {
      "name": "Dell Latitude 5580 128 GB",
      "description": "Dell Latitude 5580, 15.6\" FHD, Core i5-7300U, 16GB, 256GB SSD, Linux + Windows 10 Home",
      "price": 1178.19
    },
    
  ]
}
```

# Technical stack:
We are a microservice so that we can serve various stakeholders over HTTP protocol.  
We have leveraged the jSoup library to access & extract HTML web page documents.

# Getting Started

### Start products web scrapper
```bash
./startProductListingWebScrapper.sh
```
### Fetch all product thru REST endpoint  
```bash
./getAllProductsWhenMicroserviceIsUp.sh
```
