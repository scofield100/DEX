using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.ComponentModel.DataAnnotations;

namespace Delivery2GoBackend.Models
{
	public partial class User
	{

		public User()
		{

			DishCategories = new HashSet<DishCategory>();
			DishSizePrices = new HashSet<DishSizePrice>();
			Images = new HashSet<Image>();
			OrderDishs = new HashSet<OrderDish>();
			OrderDishAddonses = new HashSet<OrderDishAddons>();
			Permissions = new HashSet<Permission>();
			UserEntitieses = new HashSet<UserEntities>();
			EntityUpdateUser = new HashSet<Entity>();
			Addonses = new HashSet<Addons>();
			EntityMenus = new HashSet<EntityMenu>();
			Dishs = new HashSet<Dish>();
			DeliveryAgents = new HashSet<DeliveryAgent>();
			DeliveryTarifs = new HashSet<DeliveryTarif>();
			Orders = new HashSet<Order>();

		}

		public int Id { get; set; }

		[Required]
		public string Name { get; set; }

		[Required]
		public string Username { get; set; }

		[Required]
		public string Password { get; set; }

		public string SessionId { get; set; }

		public string Mobile { get; set; }

		public string Phone { get; set; }

		public string Adress { get; set; }

		public bool IsActive { get; set; }

		public DateTime CreateDate { get; set; }

		public DateTime UpdateDate { get; set; }

		public bool Superadmin { get; set; }

		public virtual ICollection<DishCategory> DishCategories {get;set;}

		public virtual ICollection<DishSizePrice> DishSizePrices {get;set;}

		public virtual ICollection<Image> Images {get;set;}

		public virtual ICollection<OrderDish> OrderDishs {get;set;}

		public virtual ICollection<OrderDishAddons> OrderDishAddonses {get;set;}

		public virtual ICollection<Permission> Permissions {get;set;}

		public virtual ICollection<UserEntities> UserEntitieses {get;set;}

		public virtual ICollection<Entity> EntityUpdateUser {get;set;}

		public virtual ICollection<Addons> Addonses {get;set;}

		public virtual ICollection<EntityMenu> EntityMenus {get;set;}

		public virtual ICollection<Dish> Dishs {get;set;}

		public virtual ICollection<DeliveryAgent> DeliveryAgents {get;set;}

		public virtual ICollection<DeliveryTarif> DeliveryTarifs {get;set;}

		public virtual ICollection<Order> Orders {get;set;}

	}
}
