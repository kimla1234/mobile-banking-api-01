package co.isatd.mobilebankingapi.features.Account_Type;

import co.isatd.mobilebankingapi.features.Account_Type.dto.AccountTypeResponse;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class AccountTypeServiceImplTest {
	@Autowired
	private AccountTypeServiceImpl accountTypeServiceImpl;

	@MockBean
	private AccountTypeRepository accountTypeRepository;

	@Test
	public void findAll() {
		List<AccountTypeResponse> expected = new ArrayList<>();
		List<AccountTypeResponse> actual = accountTypeServiceImpl.findAll();

		assertEquals(expected, actual);
	}
}
