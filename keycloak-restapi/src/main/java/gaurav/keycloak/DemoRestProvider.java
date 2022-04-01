package gaurav.keycloak;

// import gaurav.keycloak.model.UserDetails;
// import org.jboss.resteasy.annotations.cache.NoCache;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.keycloak.models.KeycloakSession;
// import org.keycloak.models.UserModel;
import org.keycloak.services.resource.RealmResourceProvider;
import org.keycloak.utils.MediaType;


// import javax.ws.rs.Encoded;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
// import java.util.List;
// import java.util.stream.Collectors;
import org.keycloak.services.managers.AppAuthManager;
import org.keycloak.services.managers.AuthenticationManager.AuthResult;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.Response;
// import java.util.Map;


public class DemoRestProvider implements RealmResourceProvider {
    private final KeycloakSession session;


    public DemoRestProvider(KeycloakSession session) {
        this.session = session;
    }


    public void close() {

    }

    public Object getResource() {
        return this;
    }

    // @GET
    // @Path("users")
    // @NoCache
    // @Produces({MediaType.APPLICATION_JSON})
    // @Encoded
    // public List<UserDetails> getUsers() {
    //     List<UserModel> userModel = session.users().getUsers(session.getContext().getRealm());
    //     return userModel.stream().map(e -> toUserDetail(e)).collect(Collectors.toList());
    // }

    // private UserDetails toUserDetail(UserModel um) {
    //     return new UserDetails(um.getUsername(), um.getFirstName(), um.getLastName());

    // }

    // http://127.0.0.1:8080/auth/realms/applications/token/IssuedFor/admin-cli/validation

	@GET
	@Path("IssuedFor/{IssuedFor}/validation")
	@Produces(MediaType.APPLICATION_JSON)
	public Response helloAuthenticated(@PathParam("IssuedFor") String IssuedFor) {
        if (IssuedFor == null)
        {
          throw new ForbiddenException("IssuedFor not set");
        }
        AuthResult auth = checkAuth(IssuedFor);
        String json = "Scopes :" + auth.getToken().getScope();
        // auth.getToken().getResourceAccess().toString();
		return Response.ok(json, MediaType.APPLICATION_JSON).build();
	}

	private AuthResult checkAuth(String Realm) {
		AuthResult auth = new AppAuthManager.BearerTokenAuthenticator(session).authenticate();
		if (auth == null) {
			throw new NotAuthorizedException("Bearer");
		} else if (auth.getToken().getIssuedFor() == null || !auth.getToken().getIssuedFor().equals(Realm)) {
			throw new ForbiddenException("Token is not properly issued for "+Realm);
		}
		return auth;
	}







}
