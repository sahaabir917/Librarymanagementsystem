package com.infinitycodehubltd.librarymanagement.book;

import com.infinitycodehubltd.librarymanagement.issuebook.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class StockOutController {

    private final IssueService issueService;

    @Autowired
    public StockOutController(IssueService issueService) {
        this.issueService = issueService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @GetMapping("/stock-out/issuers")
    public ResponseEntity<List<StockOutIssuerDTO>> getStockOutIssuers() {
        List<StockOutIssuerDTO> result = issueService.getStockOutIssuers();
        return ResponseEntity.ok(result);
    }
}
