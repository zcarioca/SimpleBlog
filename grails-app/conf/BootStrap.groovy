import net.zcarioca.simpleblog.Role
import net.zcarioca.simpleblog.User
import net.zcarioca.simpleblog.UserRole

class BootStrap {
   
   def restService

   def init = { servletContext ->
      def role = Role.findByAuthority('ROLE_USER') ?: new Role(authority:'ROLE_USER').save()

      def admin = User.findByUsername('zcarioca') ?: new User(username: 'zcarioca', password: 'password', enabled: true).save()

      if (admin.getAuthorities() == null || !admin.getAuthorities().contains(role)) {
         UserRole.create admin, role
      }
   }
   
   def destroy = {
   }
}
