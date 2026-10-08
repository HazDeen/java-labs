package edu.course.lab02;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SampleIdTest {

    @Test
    void storesValidValue() {
        SampleId id = new SampleId("sample-01");
        assertEquals("sample-01", id.value());
    }

    @Test
    void rejectsNullValue() {
        assertThrows(IllegalArgumentException.class, () -> new SampleId(null));
    }

    @Test
    void rejectsEmptyValue() {
        assertThrows(IllegalArgumentException.class, () -> new SampleId(""));
    }

    @Test
    void rejectsWhitespaceValue() {
        assertThrows(IllegalArgumentException.class, () -> new SampleId(" \t\n"));
    }

    @Test
    void equalValuesHaveEqualIdentifiersAndHashCodes() {
        SampleId first = new SampleId("sample-01");
        SampleId second = new SampleId("sample-01");

        assertNotSame(first, second);
        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void differentValuesHaveDifferentIdentifiers() {
        assertNotEquals(new SampleId("sample-01"), new SampleId("sample-02"));
    }

    @Test
    void dataSampleUsesValueIdentifierAndKeepsStringAccessor() {
        DataSample sample = new DataSample("sample-01", "T", SampleStatus.RAW, new double[]{1.0});

        assertEquals(new SampleId("sample-01"), sample.getSampleId());
        assertEquals("sample-01", sample.getId());
    }
}
