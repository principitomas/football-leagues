package com.hiringtest.footballleagues.exception;

import org.jooq.Configuration;
import org.jooq.ExecuteContext;
import org.jooq.SQLDialect;
import org.jooq.exception.DataAccessException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class ExceptionTranslatorTest {

    private ExecuteContext mockExecuteContext;

    private ExceptionTranslator exceptionTranslator;

    @Before
    public void setUp() throws Exception {
        this.mockExecuteContext = mock(ExecuteContext.class);
        exceptionTranslator = new ExceptionTranslator();
    }

    @Test
    public void testException() {
        Configuration mockConfig = mock(Configuration.class);
        doReturn(mockConfig).when(mockExecuteContext).configuration();
        doReturn(new SQLException()).when(mockExecuteContext).sqlException();
        String contextSqlString = "select * from table;";
        doReturn(contextSqlString).when(mockExecuteContext).sql();

        doReturn(SQLDialect.DEFAULT).when(mockConfig).dialect();

        exceptionTranslator.exception(mockExecuteContext);

        ArgumentCaptor<RuntimeException> exceptionArgumentCaptor = ArgumentCaptor.forClass(RuntimeException.class);
        verify(mockExecuteContext).configuration();
        verify(mockExecuteContext).sqlException();
        verify(mockExecuteContext).sql();
        verify(mockExecuteContext).exception(exceptionArgumentCaptor.capture());

        RuntimeException capturedException = exceptionArgumentCaptor.getValue();
        assertTrue(capturedException.getMessage().contains(contextSqlString));
    }
}
