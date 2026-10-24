
package com.workintech.s18d4.controller;

import com.workintech.s18d4.dto.AccountResponse;
import com.workintech.s18d4.entity.Account;
import com.workintech.s18d4.entity.Customer;
import com.workintech.s18d4.service.AccountService;
import com.workintech.s18d4.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping({"/accounts", "/account"})
public class AccountController {

    private AccountService accountService;
    private CustomerService customerService;

    @Autowired
    public AccountController(AccountService accountService,
                             CustomerService customerService) {
        this.accountService = accountService;
        this.customerService = customerService;
    }

    @GetMapping
    public List<AccountResponse> getAllAccount() {
        List<AccountResponse> responses = new ArrayList<>();

        accountService.findAll().forEach(account -> {
            responses.add(new AccountResponse(
                    account.getId(),
                    account.getAccountName(),
                    account.getMoneyAmount()
            ));
        });

        return responses;
    }

    @GetMapping("/{id}")
    public AccountResponse getAccountById(@PathVariable long id) {
        Account account = accountService.find(id);

        if (account == null) {
            return null;
        }

        return new AccountResponse(
                account.getId(),
                account.getAccountName(),
                account.getMoneyAmount()
        );
    }

    @PostMapping("/{customerId}")
    public AccountResponse addAccount(@PathVariable long customerId,
                                      @RequestBody Account account) {
        Customer customer = customerService.find(customerId);

        if (customer == null) {
            return null;
        }

        account.setCustomer(customer);
        Account savedAccount = accountService.save(account);

        return new AccountResponse(
                savedAccount.getId(),
                savedAccount.getAccountName(),
                savedAccount.getMoneyAmount()
        );
    }

    @PutMapping("/{customerId}")
    public AccountResponse updateAccount(@PathVariable long customerId,
                                         @RequestBody Account account) {
        Customer customer = customerService.find(customerId);

        if (customer == null) {
            return null;
        }

        Account existingAccount = accountService.find(account.getId());

        if (existingAccount == null) {
            return null;
        }

        existingAccount.setAccountName(account.getAccountName());
        existingAccount.setMoneyAmount(account.getMoneyAmount());
        existingAccount.setCustomer(customer);

        Account updatedAccount = accountService.save(existingAccount);

        return new AccountResponse(
                updatedAccount.getId(),
                updatedAccount.getAccountName(),
                updatedAccount.getMoneyAmount()
        );
    }

    @DeleteMapping("/{id}")
    public AccountResponse deleteAccount(@PathVariable long id) {
        Account account = accountService.find(id);

        if (account == null) {
            return null;
        }

        Account deletedAccount = accountService.delete(id);

        if (deletedAccount == null) {
            return null;
        }

        return new AccountResponse(
                deletedAccount.getId(),
                deletedAccount.getAccountName(),
                deletedAccount.getMoneyAmount()
        );
    }
}
