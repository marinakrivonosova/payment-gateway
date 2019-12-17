package ru.marina.payment.creditcard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class CreditCardDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final BeanPropertyRowMapper<CreditCard> beanPropertyRowMapper;

    @Autowired
    public CreditCardDao(final NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                         final BeanPropertyRowMapper<CreditCard> beanPropertyRowMapper) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.beanPropertyRowMapper = beanPropertyRowMapper;
    }

    public CreditCard getCreditCard(final String cardNumber) {
        final String query = "SELECT * FROM credit_cards " +
                "WHERE card_number = :cardNumber";
        final SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("cardNumber", cardNumber);
        final ConversionService sharedInstance = DefaultConversionService.getSharedInstance();

        return namedParameterJdbcTemplate.queryForObject(query, sqlParameterSource, beanPropertyRowMapper);
    }

    public void updateBalance(final String creditCardId, final BigDecimal updatedBalance) {
        final String query = "UPDATE credit_cards" +
                " SET balance = :updatedBalance" +
                " WHERE id = :creditCardId";
        final SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("creditCardId", creditCardId)
                .addValue("updatedBalance", updatedBalance);
        namedParameterJdbcTemplate.update(query, sqlParameterSource);
    }
}