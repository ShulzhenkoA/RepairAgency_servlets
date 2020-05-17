package ua.javaexternal_shulzhenko.repair_service.services.editing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

import org.mockito.MockitoAnnotations;
import ua.javaexternal_shulzhenko.repair_service.constants.CommonConstants;
import ua.javaexternal_shulzhenko.repair_service.constants.OrderStatus;
import ua.javaexternal_shulzhenko.repair_service.models.forms.OrderEditingForm;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class EditingOrderValidatorTest {

    @Mock
    private OrderEditingForm form;

    private final Set<String> inconsistencies = new HashSet<>();

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        inconsistencies.clear();
    }

    @Test
    void checkingIfNeedMaster_needCase_createMasterInconsistency() {
        when(form.getStatus()).thenReturn(OrderStatus.CAR_WAITING);
        when(form.getMasterID()).thenReturn("0");

        EditingOrderValidator.checkIfNeedMasterForThisStatus(form, inconsistencies);

        assertAll(
                () -> assertEquals(1, inconsistencies.size()),
                () -> assertTrue(inconsistencies.contains(CommonConstants.MASTER)));
    }

    @Test
    void checkingIfNeedMaster_notNeedCase_createNoInconsistencies() {
        when(form.getStatus()).thenReturn(OrderStatus.REJECTED);
        when(form.getMasterID()).thenReturn("0");

        EditingOrderValidator.checkIfNeedMasterForThisStatus(form, inconsistencies);

        assertTrue(inconsistencies.isEmpty());
    }

    @Test
    void checkingIfPrice_needCase_createPriceInconsistency() {
        when(form.getStatus()).thenReturn(OrderStatus.CAR_WAITING);
        when(form.getPrice()).thenReturn("0");

        EditingOrderValidator.checkIfNeedPreviousPrice(form, inconsistencies);

        assertAll(
                () -> assertEquals(1, inconsistencies.size()),
                () -> assertTrue(inconsistencies.contains(CommonConstants.PRICE)));
    }

    @Test
    void checkingIfNeedPrice_notNeedCase_createNoInconsistencies() {
        when(form.getStatus()).thenReturn(OrderStatus.REJECTED);
        when(form.getPrice()).thenReturn("0");

        EditingOrderValidator.checkIfNeedPreviousPrice(form, inconsistencies);

        assertTrue(inconsistencies.isEmpty());
    }
}