package cr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cr.dbo.ops.FLAW;
import cr.dbo.ops.repository.FLAWRepository;
import cr.shared.Flaw;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FlawTest {
	@Autowired
	private FLAWRepository fLAWRepository;
	@Test
	public void insertFlaw() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException, JSONException {
		BufferedReader br=new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/Flaw")));
		StringBuilder sb=new StringBuilder();
		br.lines().forEach(l->sb.append(l+"\n"));
//		String flaw="{\n   \"loc\":52556,\n   \"issueid\":143,\n   \"line\":155,\n   \"rating\":\"C\",\n   \"type\":\"javax.servlet.http.HttpServletResponse.addCookie\",\n   \"category_description\":\"Applications commonly use cryptography to implement authentication mechanisms and to ensure the confidentiality and integrity of sensitive data, both in transit and at rest.  The proper and accurate implementation of cryptography is extremely critical to its efficacy.  Configuration or coding mistakes as well as incorrect assumptions may negate a large degree of the protection it affords, leaving the crypto implementation vulnerable to attack.\nCommon cryptographic mistakes include, but are not limited to, selecting weak keys or weak cipher modes, unintentionally exposing sensitive cryptographic data, using predictable entropy sources, and mismanaging or hard-coding keys.  \nDevelopers often make the dangerous assumption that they can improve security by designing their own cryptographic algorithm; however, one of the basic tenets of cryptography is that any cipher whose effectiveness is reliant on the secrecy of the algorithm is fundamentally flawed.  \",\n   \"score\":\"76\",\n   \"cia_impact\":\"pnn\",\n   \"exploitLevel\":0,\n   \"functionrelativelocation\":46,\n   \"scope\":\"com.ryanair.api.userprofile.web.authentication.OpenSsoController\",\n   \"id\":null,\n   \"app_id\":439820,\n   \"categoryid\":12,\n   \"sourcefile\":\"OpenSsoController.java\",\n   \"severity\":2,\n   \"cwename\":\"Sensitive Cookie in HTTPS Session Without 'Secure' Attribute\",\n   \"functionprototype\":\"org.springframework.http.ResponseEntity loginSignupSocialInner(java.lang.String, boolean, common.customer.entity.ClientChannel, java.lang.String, org.springframework.validation.BindingResult, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)\",\n   \"analysis_size_bytes\":1823208,\n   \"affects_policy_compliance\":false,\n   \"cweid\":614,\n   \"pcirelated\":true,\n   \"cwe_description\":\"Setting the Secure attribute on an HTTP cookie instructs the web browser to send it only over a secure channel, such as a TLS connection.  Issuing a cookie without the Secure attribute allows the browser to transmit it over unencrypted connections, which are susceptible to eavesdropping.  It is particularly important to set the Secure attribute on any cookies containing sensitive data, such as authentication information (e.g. \\\"remember me\\\" style functionality).\",\n   \"count\":1,\n   \"sourcefilepath\":\"com/ryanair/api/userprofile/web/authentication/\",\n   \"cwepcirelated\":false,\n   \"id_commit\":10004,\n   \"recommendation_description\":\"Select the appropriate type of cryptography for the intended purpose.  Avoid proprietary encryption algorithms as they typically rely on \\\"security through obscurity\\\" rather than sound mathematics.  Select key sizes appropriate for the data being protected; for high assurance applications, 256-bit symmetric keys and 2048-bit asymmetric keys are sufficient.  Follow best practices for key storage, and ensure that plaintext data and key material are not inadvertently exposed.\",\n   \"build_id\":3215514,\n   \"categoryname\":\"Cryptographic Issues\",\n   \"flaw_description\":\"This call to javax.servlet.http.HttpServletResponse.addCookie() adds a cookie to the HTTP response that does not have the Secure attribute set. Failing to set this attribute allows the browser to send the cookie unencrypted over an HTTP session.\r\n\r\nSet the Secure attribute for all cookies used by HTTPS sessions.\r\n\r\nReferences: \r\nCWE (http://cwe.mitre.org/data/definitions/614.html) \r\nWASC (http://webappsec.pbworks.com/Insufficient-Transport-Layer-Protection)\r\n\r\n\"\n}";
		String flaw=sb.toString();
		JSONObject json=new JSONObject(sb.toString());
//		System.out.println(json);
		Flaw fj=new Flaw();
		
		Field[] field = fj.getClass().getDeclaredFields();
		
		for (int i = 0; i < field.length; i++) {
			field[i].setAccessible(true);
			
			if(json.has(field[i].getName()))
				if(json.get(field[i].getName())!=JSONObject.NULL
						&&!field[i].getName().equals("analysis_size_bytes")
						&&!field[i].getName().equals("loc")
						&&!field[i].getName().equals("count")
						&&!field[i].getName().equals("app_id")
						&&!field[i].getName().equals("build_id")
						&&!field[i].getName().equals("id_commit")
						&&!field[i].getName().equals("affects_policy_compliance")
						&&!field[i].getName().equals("category_description")
					    &&!field[i].getName().equals("recommendation_description")
						&&!field[i].getName().equals("flaw_description")
						&&!field[i].getName().equals("cwe_description")
						) {
					if(new String(json.get(field[i].getName()).toString()).length()>5)
					System.out.println(field[i].getName()+" "+new String(json.get(field[i].getName()).toString()).length());
					field[i].set(fj, json.get(field[i].getName())
//							(field[i].getType().cast(json.get(field[i].getName())) ) 
							);
				}
					
		}
		System.out.println(fj);
		System.out.println(fLAWRepository);
		FLAW f=new FLAW(fj);
		fLAWRepository.save(f);
	}
}
