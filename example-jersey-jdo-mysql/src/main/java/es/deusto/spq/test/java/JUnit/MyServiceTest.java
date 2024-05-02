//package Junit;
//
//
//import javax.ws.rs.client.Entity;
//import javax.ws.rs.client.Invocation;
//import javax.ws.rs.client.WebTarget;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import spq.serialitazion.UserData;
//
//public class MyServiceTest {
//    @Mock
//    private WebTarget webTarget;
//    
//    @InjectMocks
//    private MyService myService;
//    
//    @Before
//    public void setUp() throws Exception {
//        MockitoAnnotations.initMocks(this);
//    }
//    
//    @Test
//    public void testRegisterUser() throws Exception {
//        UserData userData = new UserData();
//        userData.setName("Javier Dominguez");
//        userData.setPassword("password");
//        userData.setPurse(100.0);
//        userData.setType(1);
//        
//        Response response = mock(Response.class);
//        when(response.getStatus()).thenReturn(Status.OK.getStatusCode());
//        
//        Invocation.Builder builder = mock(Invocation.Builder.class);
//        when(builder.post(Entity.entity(userData, MediaType.APPLICATION_JSON))).thenReturn(response);
//        
//        when(webTarget.path("register")).thenReturn(webTarget);
//        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
//        
//        myService.registerUser(userData.getName(), userData.getPassword(), userData.getPurse(), userData.getType());
//        
//        verify(webTarget).path("register");
//        verify(webTarget).request(MediaType.APPLICATION_JSON);
//        verify(builder).post(Entity.entity(userData, MediaType.APPLICATION_JSON));
//    }
//}