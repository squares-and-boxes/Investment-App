package persistence;

import model.Investment;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestJson {
    protected void checkInvestment(String type, String name, double amount, double expRet, String date, Investment i) {
        assertEquals(type,i.getType());
        assertEquals(name,i.getName());
        assertEquals(amount,i.getAmount());
        assertEquals(expRet,i.getExpReturn());
        assertEquals(date,i.getDate());
    }
}
