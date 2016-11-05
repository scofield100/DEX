package com.delivery2go.base;

import com.delivery2go.DeliveryApp;
import com.delivery2go.base.AccessAnnotation;
import com.delivery2go.base.data.repository.IUserRepository;
import com.delivery2go.base.models.Permission;
import com.delivery2go.base.models.User;
import com.enterlib.data.IEntityCollection;

import java.util.HashMap;

public class Access {

	public static final String USER_ACCESS = "USER_ACCESS";

	public static final int NONE = 0;

	public static final int READ = 1;

	public static final int WRITE = 2;

	public static final int CREATE = 4;

	public static final int DELETE = 8;

	public static final int ALL = READ|WRITE|CREATE|DELETE;

	static HashMap<String, Permission> permissions = new HashMap<>();
	static User user;

	public static boolean hasAccess(String roll, int accessFlags){
		if(user == null){
			user = DeliveryApp.getUser();
		}

        if(user == null)
            return false;

		if(user.Superadmin)
			return true;

		Permission p = permissions.get(roll);
		if(p == null)
			return false;

		int p_flags = (p.CanRead ? READ: 0) |
                    (p.CanWrite ? WRITE: 0) |
                    (p.CanCreate ? CREATE: 0) |
                    (p.CanDelete ? DELETE: 0);

		return (accessFlags & p_flags) == accessFlags;
	}

	public static void LoadAccess(IUserRepository userRep, int userId){
		permissions.clear();

		user = userRep.get(userId);
		IEntityCollection<Permission> user_permissions = user.getPermissions();
		for (Permission p : user_permissions){
			permissions.put(p.RollName, p);
		}
		user_permissions.close();
		saveAccess();
	}

	public static void saveAccess(){
		DeliveryApp app = DeliveryApp.getInstance();
		Permission[]values = new Permission[permissions.size()];
		permissions.values().toArray(values);
		app.savePreferenceObject(USER_ACCESS, values);
	}

	public static boolean restoreAccess(){
		permissions.clear();
		DeliveryApp app = DeliveryApp.getInstance();

		if(app.containsPreference(USER_ACCESS)) {
			Permission[] values = app.getPreferenceObject(USER_ACCESS, Permission[].class);
			for (Permission p : values){
				permissions.put(p.RollName, p);
			}
		}

		user = DeliveryApp.getUser();

		return  false;
	}


	public static boolean hasAccess(String[] accesses, int accessFlags){
		for (int i = 0; i < accesses.length; i++) {
			if(hasAccess(accesses[i], accessFlags))
				return true;
		}
		return false;
	}

	public static boolean hasAccess(Class<?>cls, int accessFlags){
		AccessAnnotation access = cls.getAnnotation(AccessAnnotation.class);
		if(access == null)
			return true;
		return hasAccess(access.allows(), accessFlags);
	}
}

