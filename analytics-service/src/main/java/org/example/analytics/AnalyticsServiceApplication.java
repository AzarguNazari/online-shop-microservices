package org.example.analytics;

import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class AnalyticsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnalyticsServiceApplication.class, args);
    }

}


@Setter
@Getter
@Document(collection = "salesAnalytics")
class SalesAnalytics {
    @Id
    private String id;
    private LocalDate date;
    private double totalSales;
    private String region;
    private String category;
}

@Setter
@Getter
@Document(collection = "customerAnalytics")
class CustomerAnalytics {
    @Id
    private String id;
    private LocalDate date;
    private int newCustomers;
    private int returningCustomers;
    private String region;
}

@Setter
@Getter
@Document(collection = "productAnalytics")
class ProductAnalytics {
    @Id
    private String id;
    private LocalDate date;
    private String productId;
    private String productName;
    private String category;
    private int unitsSold;
    private double totalRevenue;
}

@Setter
@Getter
class Error {
    private int code;
    private String message;
}


@Repository
interface SalesAnalyticsRepository extends MongoRepository<SalesAnalytics, String> {
    List<SalesAnalytics> findByDateBetweenAndRegionAndCategory(LocalDate startDate, LocalDate endDate, String region, String category);
}

@Repository
interface CustomerAnalyticsRepository extends MongoRepository<CustomerAnalytics, String> {
    List<CustomerAnalytics> findByDateBetweenAndRegion(LocalDate startDate, LocalDate endDate, String region);
}


@Repository
interface ProductAnalyticsRepository extends MongoRepository<ProductAnalytics, String> {
    List<ProductAnalytics> findByDateBetweenAndCategory(LocalDate startDate, LocalDate endDate, String category);
}

@Service
class AnalyticsService {

    @Autowired
    private SalesAnalyticsRepository salesAnalyticsRepository;

    @Autowired
    private CustomerAnalyticsRepository customerAnalyticsRepository;

    @Autowired
    private ProductAnalyticsRepository productAnalyticsRepository;

    public List<SalesAnalytics> getSalesAnalytics(LocalDate startDate, LocalDate endDate, String region, String category) {
        return salesAnalyticsRepository.findByDateBetweenAndRegionAndCategory(startDate, endDate, region, category);
    }

    public List<CustomerAnalytics> getCustomerAnalytics(LocalDate startDate, LocalDate endDate, String region) {
        return customerAnalyticsRepository.findByDateBetweenAndRegion(startDate, endDate, region);
    }

    public List<ProductAnalytics> getProductAnalytics(LocalDate startDate, LocalDate endDate, String category) {
        return productAnalyticsRepository.findByDateBetweenAndCategory(startDate, endDate, category);
    }
}

@RestController
@RequestMapping("/analytics")
class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping("/sales")
    public ResponseEntity<List<SalesAnalytics>> getSalesAnalytics(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
        @RequestParam(required = false) String region,
        @RequestParam(required = false) String category) {
        List<SalesAnalytics> salesAnalytics = analyticsService.getSalesAnalytics(startDate, endDate, region, category);
        return ResponseEntity.ok(salesAnalytics);
    }

    @GetMapping("/customer")
    public ResponseEntity<List<CustomerAnalytics>> getCustomerAnalytics(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
        @RequestParam(required = false) String region) {
        List<CustomerAnalytics> customerAnalytics = analyticsService.getCustomerAnalytics(startDate, endDate, region);
        return ResponseEntity.ok(customerAnalytics);
    }

    @GetMapping("/product")
    public ResponseEntity<List<ProductAnalytics>> getProductAnalytics(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
        @RequestParam(required = false) String category) {
        List<ProductAnalytics> productAnalytics = analyticsService.getProductAnalytics(startDate, endDate, category);
        return ResponseEntity.ok(productAnalytics);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleException(Exception ex) {
        Error error = new Error();
        error.setCode(500);
        error.setMessage("Internal server error.");
        return ResponseEntity.status(500).body(error);
    }
}
