using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Common;
using System.Data.Entity;
using System.Data.Entity.ModelConfiguration.Conventions;
using System.Linq;
using System.Text;

namespace Delivery2GoBackend.Models
{
	public partial class deliveryContext :DbContext
	{
		public deliveryContext()
			:base("Name=DefaultConnection")
		{

			Configuration.LazyLoadingEnabled = false;

		}

		public DbSet<EntityCategory> EntityCategories { get; set; }

		public DbSet<State> States { get; set; }

		public DbSet<EntityImages> EntityImageses { get; set; }

		public DbSet<EntityCategories> EntityCategorieses { get; set; }

		public DbSet<DishCategories> DishCategorieses { get; set; }

		public DbSet<DishImages> DishImageses { get; set; }

		public DbSet<City> Cities { get; set; }

		public DbSet<ClientDishFavorites> ClientDishFavoriteses { get; set; }

		public DbSet<ClientEntityFavorites> ClientEntityFavoriteses { get; set; }

		public DbSet<OrderState> OrderStates { get; set; }

		public DbSet<OrderType> OrderTypes { get; set; }

		public DbSet<DishSize> DishSizes { get; set; }

		public DbSet<Roll> Rolls { get; set; }

		public DbSet<DishCategory> DishCategories { get; set; }

		public DbSet<DishSizePrice> DishSizePrices { get; set; }

		public DbSet<Image> Images { get; set; }

		public DbSet<OrderDish> OrderDishs { get; set; }

		public DbSet<OrderDishAddons> OrderDishAddonses { get; set; }

		public DbSet<Permission> Permissions { get; set; }

		public DbSet<User> Users { get; set; }

		public DbSet<UserEntities> UserEntitieses { get; set; }

		public DbSet<Entity> Entities { get; set; }

		public DbSet<Addons> Addonses { get; set; }

		public DbSet<EntityMenu> EntityMenus { get; set; }

		public DbSet<EntityReview> EntityReviews { get; set; }

		public DbSet<DishReview> DishReviews { get; set; }

		public DbSet<Dish> Dishs { get; set; }

		public DbSet<DeliveryAgent> DeliveryAgents { get; set; }

		public DbSet<DeliveryTarif> DeliveryTarifs { get; set; }

		public DbSet<Order> Orders { get; set; }

		public DbSet<Client> Clients { get; set; }

		protected override void OnModelCreating(DbModelBuilder modelBuilder)
		{

			#region EntityCategory

			var EntityCategoryConfig = modelBuilder.Entity<EntityCategory>();
			EntityCategoryConfig.ToTable("EntityCategory");

			EntityCategoryConfig.HasKey(x => x.Id);

			EntityCategoryConfig.Property(x=>x.Id)
				.HasColumnName("Id")
				.IsRequired();

			EntityCategoryConfig.Property(x=>x.Code)
				.HasColumnName("Code")
				.IsRequired();

			EntityCategoryConfig.Property(x=>x.Name)
				.HasColumnName("Name")
				.IsRequired();

			#endregion EntityCategory

			#region State

			var StateConfig = modelBuilder.Entity<State>();
			StateConfig.ToTable("State");

			StateConfig.HasKey(x => x.Id);

			StateConfig.Property(x=>x.Id)
				.HasColumnName("Id")
				.IsRequired();

			StateConfig.Property(x=>x.Name)
				.HasColumnName("Name")
				.IsRequired();

			#endregion State

			#region EntityImages

			var EntityImagesConfig = modelBuilder.Entity<EntityImages>();
			EntityImagesConfig.ToTable("EntityImages");

			EntityImagesConfig.HasKey(x => new { x.EntityId, x.ImageId });

			EntityImagesConfig.Property(x=>x.EntityId)
				.HasColumnName("EntityId")
				.HasColumnOrder(0)
				.IsRequired();

			EntityImagesConfig.HasRequired(x => x.Entity)
				.WithMany(t=>t.EntityImageses)
				.HasForeignKey(x => x.EntityId);

			EntityImagesConfig.Property(x=>x.ImageId)
				.HasColumnName("ImageId")
				.HasColumnOrder(1)
				.IsRequired();

			EntityImagesConfig.HasRequired(x => x.Image)
				.WithMany(t=>t.EntityImageses)
				.HasForeignKey(x => x.ImageId);

			#endregion EntityImages

			#region EntityCategories

			var EntityCategoriesConfig = modelBuilder.Entity<EntityCategories>();
			EntityCategoriesConfig.ToTable("EntityCategories");

			EntityCategoriesConfig.HasKey(x => new { x.EntityId, x.CategoryId });

			EntityCategoriesConfig.Property(x=>x.EntityId)
				.HasColumnName("EntityId")
				.HasColumnOrder(0)
				.IsRequired();

			EntityCategoriesConfig.HasRequired(x => x.Entity)
				.WithMany(t=>t.EntityCategorieses)
				.HasForeignKey(x => x.EntityId);

			EntityCategoriesConfig.Property(x=>x.CategoryId)
				.HasColumnName("CategoryId")
				.HasColumnOrder(1)
				.IsRequired();

			EntityCategoriesConfig.HasRequired(x => x.Category)
				.WithMany(t=>t.EntityCategorieses)
				.HasForeignKey(x => x.CategoryId);

			#endregion EntityCategories

			#region DishCategories

			var DishCategoriesConfig = modelBuilder.Entity<DishCategories>();
			DishCategoriesConfig.ToTable("DishCategories");

			DishCategoriesConfig.HasKey(x => new { x.DishId, x.CategoryId });

			DishCategoriesConfig.Property(x=>x.DishId)
				.HasColumnName("DishId")
				.HasColumnOrder(0)
				.IsRequired();

			DishCategoriesConfig.HasRequired(x => x.Dish)
				.WithMany(t=>t.DishCategorieses)
				.HasForeignKey(x => x.DishId);

			DishCategoriesConfig.Property(x=>x.CategoryId)
				.HasColumnName("CategoryId")
				.HasColumnOrder(1)
				.IsRequired();

			DishCategoriesConfig.HasRequired(x => x.Category)
				.WithMany(t=>t.DishCategorieses)
				.HasForeignKey(x => x.CategoryId);

			#endregion DishCategories

			#region DishImages

			var DishImagesConfig = modelBuilder.Entity<DishImages>();
			DishImagesConfig.ToTable("DishImages");

			DishImagesConfig.HasKey(x => new { x.DishId, x.ImageId });

			DishImagesConfig.Property(x=>x.DishId)
				.HasColumnName("DishId")
				.HasColumnOrder(0)
				.IsRequired();

			DishImagesConfig.HasRequired(x => x.Dish)
				.WithMany(t=>t.DishImageses)
				.HasForeignKey(x => x.DishId);

			DishImagesConfig.Property(x=>x.ImageId)
				.HasColumnName("ImageId")
				.HasColumnOrder(1)
				.IsRequired();

			DishImagesConfig.HasRequired(x => x.Image)
				.WithMany(t=>t.DishImageses)
				.HasForeignKey(x => x.ImageId);

			#endregion DishImages

			#region City

			var CityConfig = modelBuilder.Entity<City>();
			CityConfig.ToTable("City");

			CityConfig.HasKey(x => x.Id);

			CityConfig.Property(x=>x.Id)
				.HasColumnName("Id")
				.IsRequired();

			CityConfig.Property(x=>x.Name)
				.HasColumnName("Name")
				.IsRequired();

			CityConfig.Property(x=>x.StateId)
				.HasColumnName("StateId")
				.IsRequired();

			CityConfig.HasRequired(x => x.State)
				.WithMany(t=>t.Cities)
				.HasForeignKey(x => x.StateId);

			#endregion City

			#region ClientDishFavorites

			var ClientDishFavoritesConfig = modelBuilder.Entity<ClientDishFavorites>();
			ClientDishFavoritesConfig.ToTable("ClientDishFavorites");

			ClientDishFavoritesConfig.HasKey(x => new { x.ClientId, x.DishId });

			ClientDishFavoritesConfig.Property(x=>x.ClientId)
				.HasColumnName("ClientId")
				.HasColumnOrder(0)
				.IsRequired();

			ClientDishFavoritesConfig.HasRequired(x => x.Client)
				.WithMany(t=>t.ClientDishFavoriteses)
				.HasForeignKey(x => x.ClientId);

			ClientDishFavoritesConfig.Property(x=>x.DishId)
				.HasColumnName("DishId")
				.HasColumnOrder(1)
				.IsRequired();

			ClientDishFavoritesConfig.HasRequired(x => x.Dish)
				.WithMany(t=>t.ClientDishFavoriteses)
				.HasForeignKey(x => x.DishId);

			#endregion ClientDishFavorites

			#region ClientEntityFavorites

			var ClientEntityFavoritesConfig = modelBuilder.Entity<ClientEntityFavorites>();
			ClientEntityFavoritesConfig.ToTable("ClientEntityFavorites");

			ClientEntityFavoritesConfig.HasKey(x => new { x.ClientId, x.EntityId });

			ClientEntityFavoritesConfig.Property(x=>x.ClientId)
				.HasColumnName("ClientId")
				.HasColumnOrder(0)
				.IsRequired();

			ClientEntityFavoritesConfig.HasRequired(x => x.Client)
				.WithMany(t=>t.ClientEntityFavoriteses)
				.HasForeignKey(x => x.ClientId);

			ClientEntityFavoritesConfig.Property(x=>x.EntityId)
				.HasColumnName("EntityId")
				.HasColumnOrder(1)
				.IsRequired();

			ClientEntityFavoritesConfig.HasRequired(x => x.Entity)
				.WithMany(t=>t.ClientEntityFavoriteses)
				.HasForeignKey(x => x.EntityId);

			#endregion ClientEntityFavorites

			#region OrderState

			var OrderStateConfig = modelBuilder.Entity<OrderState>();
			OrderStateConfig.ToTable("OrderState");

			OrderStateConfig.HasKey(x => x.Id);

			OrderStateConfig.Property(x=>x.Id)
				.HasColumnName("Id")
				.IsRequired();

			OrderStateConfig.Property(x=>x.Code)
				.HasColumnName("Code")
				.IsRequired();

			OrderStateConfig.Property(x=>x.Name)
				.HasColumnName("Name")
				.IsRequired();

			#endregion OrderState

			#region OrderType

			var OrderTypeConfig = modelBuilder.Entity<OrderType>();
			OrderTypeConfig.ToTable("OrderType");

			OrderTypeConfig.HasKey(x => x.Id);

			OrderTypeConfig.Property(x=>x.Id)
				.HasColumnName("Id")
				.IsRequired();

			OrderTypeConfig.Property(x=>x.Code)
				.HasColumnName("Code")
				.IsRequired();

			OrderTypeConfig.Property(x=>x.Name)
				.HasColumnName("Name")
				.IsRequired();

			#endregion OrderType

			#region DishSize

			var DishSizeConfig = modelBuilder.Entity<DishSize>();
			DishSizeConfig.ToTable("DishSize");

			DishSizeConfig.HasKey(x => x.Id);

			DishSizeConfig.Property(x=>x.Id)
				.HasColumnName("Id")
				.IsRequired();

			DishSizeConfig.Property(x=>x.Code)
				.HasColumnName("Code")
				.IsRequired();

			DishSizeConfig.Property(x=>x.Name)
				.HasColumnName("Name")
				.IsRequired();

			#endregion DishSize

			#region Roll

			var RollConfig = modelBuilder.Entity<Roll>();
			RollConfig.ToTable("Roll");

			RollConfig.HasKey(x => x.Id);

			RollConfig.Property(x=>x.Id)
				.HasColumnName("Id")
				.IsRequired();

			RollConfig.Property(x=>x.Name)
				.HasColumnName("Name")
				.IsRequired();

			RollConfig.Property(x=>x.UpdateDate)
				.HasColumnName("UpdateDate")
				.IsRequired();

			#endregion Roll

			#region DishCategory

			var DishCategoryConfig = modelBuilder.Entity<DishCategory>();
			DishCategoryConfig.ToTable("DishCategory");

			DishCategoryConfig.HasKey(x => x.Id);

			DishCategoryConfig.Property(x=>x.Id)
				.HasColumnName("Id")
				.IsRequired();

			DishCategoryConfig.Property(x=>x.Code)
				.HasColumnName("Code")
				.IsRequired();

			DishCategoryConfig.Property(x=>x.Name)
				.HasColumnName("Name")
				.IsRequired();

			DishCategoryConfig.Property(x=>x.UpdateUserId)
				.HasColumnName("UpdateUserId")
				.IsOptional();

			DishCategoryConfig.HasOptional(x => x.UpdateUser)
				.WithMany(t=>t.DishCategories)
				.HasForeignKey(x => x.UpdateUserId);

			DishCategoryConfig.Property(x=>x.UpdateDate)
				.HasColumnName("UpdateDate")
				.IsRequired();

			#endregion DishCategory

			#region DishSizePrice

			var DishSizePriceConfig = modelBuilder.Entity<DishSizePrice>();
			DishSizePriceConfig.ToTable("DishSizePrice");

			DishSizePriceConfig.HasKey(x => x.Id);

			DishSizePriceConfig.Property(x=>x.Id)
				.HasColumnName("Id")
				.IsRequired();

			DishSizePriceConfig.Property(x=>x.SizeId)
				.HasColumnName("SizeId")
				.IsRequired();

			DishSizePriceConfig.HasRequired(x => x.Size)
				.WithMany(t=>t.DishSizePrices)
				.HasForeignKey(x => x.SizeId);

			DishSizePriceConfig.Property(x=>x.DishId)
				.HasColumnName("DishId")
				.IsRequired();

			DishSizePriceConfig.HasRequired(x => x.Dish)
				.WithMany(t=>t.DishSizePrices)
				.HasForeignKey(x => x.DishId);

			DishSizePriceConfig.Property(x=>x.ExtraPrice)
				.HasColumnName("ExtraPrice")
				.IsRequired();

			DishSizePriceConfig.Property(x=>x.AvailableCount)
				.HasColumnName("AvailableCount")
				.IsOptional();

			DishSizePriceConfig.Property(x=>x.UpdateDate)
				.HasColumnName("UpdateDate")
				.IsRequired();

			DishSizePriceConfig.Property(x=>x.UpdateUserId)
				.HasColumnName("UpdateUserId")
				.IsOptional();

			DishSizePriceConfig.HasOptional(x => x.UpdateUser)
				.WithMany(t=>t.DishSizePrices)
				.HasForeignKey(x => x.UpdateUserId);

			#endregion DishSizePrice

			#region Image

			var ImageConfig = modelBuilder.Entity<Image>();
			ImageConfig.ToTable("Image");

			ImageConfig.HasKey(x => x.Id);

			ImageConfig.Property(x=>x.Id)
				.HasColumnName("Id")
				.IsRequired();

			ImageConfig.Property(x=>x.Location)
				.HasColumnName("Location")
				.IsRequired();

			ImageConfig.Property(x=>x.UpdateUserId)
				.HasColumnName("UpdateUserId")
				.IsOptional();

            ImageConfig.Property(x => x.ImageData)
                .HasColumnName("ImageData")
				.IsOptional();

			ImageConfig.HasOptional(x => x.UpdateUser)
				.WithMany(t=>t.Images)
				.HasForeignKey(x => x.UpdateUserId);

			ImageConfig.Property(x=>x.UpdateDate)
				.HasColumnName("UpdateDate")
				.IsRequired();

			#endregion Image

			#region OrderDish

			var OrderDishConfig = modelBuilder.Entity<OrderDish>();
			OrderDishConfig.ToTable("OrderDish");

			OrderDishConfig.HasKey(x => x.Id);

			OrderDishConfig.Property(x=>x.Id)
				.HasColumnName("Id")
				.IsRequired();

			OrderDishConfig.Property(x=>x.OrderId)
				.HasColumnName("OrderId")
				.IsRequired();

			OrderDishConfig.HasRequired(x => x.Order)
				.WithMany(t=>t.OrderDishs)
				.HasForeignKey(x => x.OrderId);

			OrderDishConfig.Property(x=>x.DishId)
				.HasColumnName("DishId")
				.IsRequired();

			OrderDishConfig.HasRequired(x => x.Dish)
				.WithMany(t=>t.OrderDishs)
				.HasForeignKey(x => x.DishId);

			OrderDishConfig.Property(x=>x.DishPrice)
				.HasColumnName("DishPrice")
				.IsRequired();

			OrderDishConfig.Property(x=>x.Quantity)
				.HasColumnName("Quantity")
				.IsRequired();

			OrderDishConfig.Property(x=>x.SizeId)
				.HasColumnName("SizeId")
				.IsOptional();

			OrderDishConfig.HasOptional(x => x.Size)
				.WithMany(t=>t.OrderDishs)
				.HasForeignKey(x => x.SizeId);

			OrderDishConfig.Property(x=>x.DressingId)
				.HasColumnName("DressingId")
				.IsOptional();

			OrderDishConfig.HasOptional(x => x.Dressing)
				.WithMany(t=>t.OrderDishs)
				.HasForeignKey(x => x.DressingId);

			OrderDishConfig.Property(x=>x.DressingPrice)
				.HasColumnName("DressingPrice")
				.IsOptional();

			OrderDishConfig.Property(x=>x.SubTotal)
				.HasColumnName("SubTotal")
				.IsOptional();

			OrderDishConfig.Property(x=>x.UpdateDate)
				.HasColumnName("UpdateDate")
				.IsRequired();

			OrderDishConfig.Property(x=>x.Remarks)
				.HasColumnName("Remarks")
				.IsOptional();

			OrderDishConfig.Property(x=>x.UpdateUserId)
				.HasColumnName("UpdateUserId")
				.IsOptional();

			OrderDishConfig.HasOptional(x => x.UpdateUser)
				.WithMany(t=>t.OrderDishs)
				.HasForeignKey(x => x.UpdateUserId);

			#endregion OrderDish

			#region OrderDishAddons

			var OrderDishAddonsConfig = modelBuilder.Entity<OrderDishAddons>();
			OrderDishAddonsConfig.ToTable("OrderDishAddons");

			OrderDishAddonsConfig.HasKey(x => new { x.OrderDishId, x.AddonId });

			OrderDishAddonsConfig.Property(x=>x.OrderDishId)
				.HasColumnName("OrderDishId")
				.HasColumnOrder(0)
				.IsRequired();

			OrderDishAddonsConfig.HasRequired(x => x.OrderDish)
				.WithMany(t=>t.OrderDishAddonses)
				.HasForeignKey(x => x.OrderDishId);

			OrderDishAddonsConfig.Property(x=>x.AddonId)
				.HasColumnName("AddonId")
				.HasColumnOrder(1)
				.IsRequired();

			OrderDishAddonsConfig.HasRequired(x => x.Addon)
				.WithMany(t=>t.OrderDishAddonses)
				.HasForeignKey(x => x.AddonId);

			OrderDishAddonsConfig.Property(x=>x.AddonPrice)
				.HasColumnName("AddonPrice")
				.IsRequired();

			OrderDishAddonsConfig.Property(x=>x.UpdateDate)
				.HasColumnName("UpdateDate")
				.IsRequired();

			OrderDishAddonsConfig.Property(x=>x.Remarks)
				.HasColumnName("Remarks")
				.IsOptional();

			OrderDishAddonsConfig.Property(x=>x.UpdateUserId)
				.HasColumnName("UpdateUserId")
				.IsOptional();

			OrderDishAddonsConfig.HasOptional(x => x.UpdateUser)
				.WithMany(t=>t.OrderDishAddonses)
				.HasForeignKey(x => x.UpdateUserId);

			#endregion OrderDishAddons

			#region Permission

			var PermissionConfig = modelBuilder.Entity<Permission>();
			PermissionConfig.ToTable("Permission");

			PermissionConfig.HasKey(x => x.Id);

			PermissionConfig.Property(x=>x.Id)
				.HasColumnName("Id")
				.IsRequired();

			PermissionConfig.Property(x=>x.RollId)
				.HasColumnName("RollId")
				.IsRequired();

			PermissionConfig.HasRequired(x => x.Roll)
				.WithMany(t=>t.Permissions)
				.HasForeignKey(x => x.RollId);

			PermissionConfig.Property(x=>x.UserId)
				.HasColumnName("UserId")
				.IsRequired();

			PermissionConfig.HasRequired(x => x.User)
				.WithMany(t=>t.Permissions)
				.HasForeignKey(x => x.UserId);

			PermissionConfig.Property(x=>x.CanRead)
				.HasColumnName("CanRead")
				.IsRequired();

			PermissionConfig.Property(x=>x.CanWrite)
				.HasColumnName("CanWrite")
				.IsRequired();

			PermissionConfig.Property(x=>x.CanCreate)
				.HasColumnName("CanCreate")
				.IsRequired();

			PermissionConfig.Property(x=>x.CanDelete)
				.HasColumnName("CanDelete")
				.IsRequired();

			PermissionConfig.Property(x=>x.CreateDate)
				.HasColumnName("CreateDate")
				.IsRequired();

			PermissionConfig.Property(x=>x.UpdateDate)
				.HasColumnName("UpdateDate")
				.IsRequired();

			#endregion Permission

			#region User

			var UserConfig = modelBuilder.Entity<User>();
			UserConfig.ToTable("User");

			UserConfig.HasKey(x => x.Id);

			UserConfig.Property(x=>x.Id)
				.HasColumnName("Id")
				.IsRequired();

			UserConfig.Property(x=>x.Name)
				.HasColumnName("Name")
				.IsRequired();

			UserConfig.Property(x=>x.Username)
				.HasColumnName("Username")
				.IsRequired();

			UserConfig.Property(x=>x.Password)
				.HasColumnName("Password")
				.IsRequired();

			UserConfig.Property(x=>x.SessionId)
				.HasColumnName("SessionId")
				.IsOptional();

			UserConfig.Property(x=>x.Mobile)
				.HasColumnName("Mobile")
				.IsOptional();

			UserConfig.Property(x=>x.Phone)
				.HasColumnName("Phone")
				.IsOptional();

			UserConfig.Property(x=>x.Adress)
				.HasColumnName("Adress")
				.IsOptional();

			UserConfig.Property(x=>x.IsActive)
				.HasColumnName("IsActive")
				.IsRequired();

			UserConfig.Property(x=>x.CreateDate)
				.HasColumnName("CreateDate")
				.IsRequired();

			UserConfig.Property(x=>x.UpdateDate)
				.HasColumnName("UpdateDate")
				.IsRequired();

			UserConfig.Property(x=>x.Superadmin)
				.HasColumnName("Superadmin")
				.IsRequired();

			#endregion User

			#region UserEntities

			var UserEntitiesConfig = modelBuilder.Entity<UserEntities>();
			UserEntitiesConfig.ToTable("UserEntities");

			UserEntitiesConfig.HasKey(x => new { x.UserId, x.EntityId });

			UserEntitiesConfig.Property(x=>x.UserId)
				.HasColumnName("UserId")
				.HasColumnOrder(0)
				.IsRequired();

			UserEntitiesConfig.HasRequired(x => x.User)
				.WithMany(t=>t.UserEntitieses)
				.HasForeignKey(x => x.UserId);

			UserEntitiesConfig.Property(x=>x.EntityId)
				.HasColumnName("EntityId")
				.HasColumnOrder(1)
				.IsRequired();

			UserEntitiesConfig.HasRequired(x => x.Entity)
				.WithMany(t=>t.UserEntitieses)
				.HasForeignKey(x => x.EntityId);

			#endregion UserEntities

			#region Entity

			var EntityConfig = modelBuilder.Entity<Entity>();
			EntityConfig.ToTable("Entity");

			EntityConfig.HasKey(x => x.Id);

			EntityConfig.Property(x=>x.Id)
				.HasColumnName("Id")
				.IsRequired();

			EntityConfig.Property(x=>x.Name)
				.HasColumnName("Name")
				.IsRequired();

			EntityConfig.Property(x=>x.Ranking)
				.HasColumnName("Ranking")
				.IsOptional();

			EntityConfig.Property(x=>x.ReviewCount)
				.HasColumnName("ReviewCount")
				.IsRequired();

			EntityConfig.Property(x=>x.ImageId)
				.HasColumnName("ImageId")
				.IsOptional();

			EntityConfig.HasOptional(x => x.Image)
				.WithMany(t=>t.EntityImage)
				.HasForeignKey(x => x.ImageId);

			EntityConfig.Property(x=>x.Adress)
				.HasColumnName("Adress")
				.IsOptional();

			EntityConfig.Property(x=>x.CityId)
				.HasColumnName("CityId")
				.IsOptional();

			EntityConfig.HasOptional(x => x.City)
				.WithMany(t=>t.Entities)
				.HasForeignKey(x => x.CityId);

			EntityConfig.Property(x=>x.StateId)
				.HasColumnName("StateId")
				.IsOptional();

			EntityConfig.HasOptional(x => x.State)
				.WithMany(t=>t.Entities)
				.HasForeignKey(x => x.StateId);

			EntityConfig.Property(x=>x.Lat)
				.HasColumnName("Lat")
				.IsOptional();

			EntityConfig.Property(x=>x.Lng)
				.HasColumnName("Lng")
				.IsOptional();

			EntityConfig.Property(x=>x.Phone)
				.HasColumnName("Phone")
				.IsOptional();

			EntityConfig.Property(x=>x.Email)
				.HasColumnName("Email")
				.IsOptional();

			EntityConfig.Property(x=>x.Tags)
				.HasColumnName("Tags")
				.IsOptional();

			EntityConfig.Property(x=>x.OpeningTime)
				.HasColumnName("OpeningTime")
				.IsOptional();

			EntityConfig.Property(x=>x.CloseTime)
				.HasColumnName("CloseTime")
				.IsOptional();

			EntityConfig.Property(x=>x.Description)
				.HasColumnName("Description")
				.IsOptional();

			EntityConfig.Property(x=>x.IsAvailable)
				.HasColumnName("IsAvailable")
				.IsRequired();

			EntityConfig.Property(x=>x.DeliveryPrice)
				.HasColumnName("DeliveryPrice")
				.IsOptional();

			EntityConfig.Property(x=>x.DeliveryTimeMin)
				.HasColumnName("DeliveryTimeMin")
				.IsOptional();

			EntityConfig.Property(x=>x.DeliveryTimeMax)
				.HasColumnName("DeliveryTimeMax")
				.IsOptional();

			EntityConfig.Property(x=>x.MinPrice)
				.HasColumnName("MinPrice")
				.IsOptional();

			EntityConfig.Property(x=>x.CreateDate)
				.HasColumnName("CreateDate")
				.IsOptional();

			EntityConfig.Property(x=>x.HasDelivery)
				.HasColumnName("HasDelivery")
				.IsRequired();

			EntityConfig.Property(x=>x.HasPickup)
				.HasColumnName("HasPickup")
				.IsRequired();

			EntityConfig.Property(x=>x.Account)
				.HasColumnName("Account")
				.IsOptional();

			EntityConfig.Property(x=>x.UpdateUserId)
				.HasColumnName("UpdateUserId")
				.IsOptional();

			EntityConfig.HasOptional(x => x.UpdateUser)
				.WithMany(t=>t.EntityUpdateUser)
				.HasForeignKey(x => x.UpdateUserId);

			EntityConfig.Property(x=>x.UpdateDate)
				.HasColumnName("UpdateDate")
				.IsRequired();

			#endregion Entity

			#region Addons

			var AddonsConfig = modelBuilder.Entity<Addons>();
			AddonsConfig.ToTable("Addons");

			AddonsConfig.HasKey(x => x.Id);

			AddonsConfig.Property(x=>x.Id)
				.HasColumnName("Id")
				.IsRequired();

			AddonsConfig.Property(x=>x.Name)
				.HasColumnName("Name")
				.IsRequired();

			AddonsConfig.Property(x=>x.EntityId)
				.HasColumnName("EntityId")
				.IsRequired();

			AddonsConfig.HasRequired(x => x.Entity)
				.WithMany(t=>t.Addonses)
				.HasForeignKey(x => x.EntityId);

			AddonsConfig.Property(x=>x.Price)
				.HasColumnName("Price")
				.IsRequired();

			AddonsConfig.Property(x=>x.IsAvailable)
				.HasColumnName("IsAvailable")
				.IsRequired();

			AddonsConfig.Property(x=>x.IsDressing)
				.HasColumnName("IsDressing")
				.IsRequired();

			AddonsConfig.Property(x=>x.AvailableCount)
				.HasColumnName("AvailableCount")
				.IsOptional();

			AddonsConfig.Property(x=>x.UpdateUserId)
				.HasColumnName("UpdateUserId")
				.IsOptional();

			AddonsConfig.HasOptional(x => x.UpdateUser)
				.WithMany(t=>t.Addonses)
				.HasForeignKey(x => x.UpdateUserId);

			AddonsConfig.Property(x=>x.UpdateDate)
				.HasColumnName("UpdateDate")
				.IsRequired();

			AddonsConfig.Property(x=>x.CreateDate)
				.HasColumnName("CreateDate")
				.IsRequired();

			#endregion Addons

			#region EntityMenu

			var EntityMenuConfig = modelBuilder.Entity<EntityMenu>();
			EntityMenuConfig.ToTable("EntityMenu");

			EntityMenuConfig.HasKey(x => x.Id);

			EntityMenuConfig.Property(x=>x.Id)
				.HasColumnName("Id")
				.IsRequired();

			EntityMenuConfig.Property(x=>x.Name)
				.HasColumnName("Name")
				.IsRequired();

			EntityMenuConfig.Property(x=>x.EntityId)
				.HasColumnName("EntityId")
				.IsRequired();

			EntityMenuConfig.HasRequired(x => x.Entity)
				.WithMany(t=>t.EntityMenus)
				.HasForeignKey(x => x.EntityId);

			EntityMenuConfig.Property(x=>x.IsSpecial)
				.HasColumnName("IsSpecial")
				.IsRequired();

			EntityMenuConfig.Property(x=>x.IsAvailable)
				.HasColumnName("IsAvailable")
				.IsRequired();

			EntityMenuConfig.Property(x=>x.ImageId)
				.HasColumnName("ImageId")
				.IsOptional();

			EntityMenuConfig.HasOptional(x => x.Image)
				.WithMany(t=>t.EntityMenus)
				.HasForeignKey(x => x.ImageId);

			EntityMenuConfig.Property(x=>x.UpdateUserId)
				.HasColumnName("UpdateUserId")
				.IsOptional();

			EntityMenuConfig.HasOptional(x => x.UpdateUser)
				.WithMany(t=>t.EntityMenus)
				.HasForeignKey(x => x.UpdateUserId);

			EntityMenuConfig.Property(x=>x.UpdateDate)
				.HasColumnName("UpdateDate")
				.IsRequired();

			EntityMenuConfig.Property(x=>x.CreateDate)
				.HasColumnName("CreateDate")
				.IsRequired();

			#endregion EntityMenu

			#region EntityReview

			var EntityReviewConfig = modelBuilder.Entity<EntityReview>();
			EntityReviewConfig.ToTable("EntityReview");

			EntityReviewConfig.HasKey(x => x.Id);

			EntityReviewConfig.Property(x=>x.Id)
				.HasColumnName("Id")
				.IsRequired();

			EntityReviewConfig.Property(x=>x.Rating)
				.HasColumnName("Rating")
				.IsRequired();

			EntityReviewConfig.Property(x=>x.ClientId)
				.HasColumnName("ClientId")
				.IsRequired();

			EntityReviewConfig.HasRequired(x => x.Client)
				.WithMany(t=>t.EntityReviews)
				.HasForeignKey(x => x.ClientId);

			EntityReviewConfig.Property(x=>x.EntityId)
				.HasColumnName("EntityId")
				.IsRequired();

			EntityReviewConfig.HasRequired(x => x.Entity)
				.WithMany(t=>t.EntityReviews)
				.HasForeignKey(x => x.EntityId);

			EntityReviewConfig.Property(x=>x.Description)
				.HasColumnName("Description")
				.IsRequired();

			EntityReviewConfig.Property(x=>x.UpdateDate)
				.HasColumnName("UpdateDate")
				.IsRequired();

			#endregion EntityReview

			#region DishReview

			var DishReviewConfig = modelBuilder.Entity<DishReview>();
			DishReviewConfig.ToTable("DishReview");

			DishReviewConfig.HasKey(x => x.Id);

			DishReviewConfig.Property(x=>x.Id)
				.HasColumnName("Id")
				.IsRequired();

			DishReviewConfig.Property(x=>x.Rating)
				.HasColumnName("Rating")
				.IsRequired();

			DishReviewConfig.Property(x=>x.ClientId)
				.HasColumnName("ClientId")
				.IsRequired();

			DishReviewConfig.HasRequired(x => x.Client)
				.WithMany(t=>t.DishReviews)
				.HasForeignKey(x => x.ClientId);

			DishReviewConfig.Property(x=>x.DishId)
				.HasColumnName("DishId")
				.IsRequired();

			DishReviewConfig.HasRequired(x => x.Dish)
				.WithMany(t=>t.DishReviews)
				.HasForeignKey(x => x.DishId);

			DishReviewConfig.Property(x=>x.Description)
				.HasColumnName("Description")
				.IsRequired();

			DishReviewConfig.Property(x=>x.UpdateDate)
				.HasColumnName("UpdateDate")
				.IsRequired();

			#endregion DishReview

			#region Dish

			var DishConfig = modelBuilder.Entity<Dish>();
			DishConfig.ToTable("Dish");

			DishConfig.HasKey(x => x.Id);

			DishConfig.Property(x=>x.Id)
				.HasColumnName("Id")
				.IsRequired();

			DishConfig.Property(x=>x.Name)
				.HasColumnName("Name")
				.IsRequired();

			DishConfig.Property(x=>x.IsAvailable)
				.HasColumnName("IsAvailable")
				.IsRequired();

			DishConfig.Property(x=>x.ImageId)
				.HasColumnName("ImageId")
				.IsOptional();

			DishConfig.HasOptional(x => x.Image)
				.WithMany(t=>t.DishImage)
				.HasForeignKey(x => x.ImageId);

			DishConfig.Property(x=>x.Ranking)
				.HasColumnName("Ranking")
				.IsOptional();

			DishConfig.Property(x=>x.ReviewCount)
				.HasColumnName("ReviewCount")
				.IsOptional();

			DishConfig.Property(x=>x.OrderCount)
				.HasColumnName("OrderCount")
				.IsRequired();

			DishConfig.Property(x=>x.Price)
				.HasColumnName("Price")
				.IsRequired();

			DishConfig.Property(x=>x.EntityId)
				.HasColumnName("EntityId")
				.IsRequired();

			DishConfig.HasRequired(x => x.Entity)
				.WithMany(t=>t.Dishs)
				.HasForeignKey(x => x.EntityId);

			DishConfig.Property(x=>x.InWhatsGood)
				.HasColumnName("InWhatsGood")
				.IsRequired();

			DishConfig.Property(x=>x.Description)
				.HasColumnName("Description")
				.IsOptional();

			DishConfig.Property(x=>x.AvailableCount)
				.HasColumnName("AvailableCount")
				.IsOptional();

			DishConfig.Property(x=>x.Tags)
				.HasColumnName("Tags")
				.IsOptional();

			DishConfig.Property(x=>x.MenuId)
				.HasColumnName("MenuId")
				.IsOptional();

			DishConfig.HasOptional(x => x.Menu)
				.WithMany(t=>t.Dishs)
				.HasForeignKey(x => x.MenuId);

			DishConfig.Property(x=>x.UpdateUserId)
				.HasColumnName("UpdateUserId")
				.IsOptional();

			DishConfig.HasOptional(x => x.UpdateUser)
				.WithMany(t=>t.Dishs)
				.HasForeignKey(x => x.UpdateUserId);

			DishConfig.Property(x=>x.UpdateDate)
				.HasColumnName("UpdateDate")
				.IsRequired();

			DishConfig.Property(x=>x.CreateDate)
				.HasColumnName("CreateDate")
				.IsRequired();

			#endregion Dish

			#region DeliveryAgent

			var DeliveryAgentConfig = modelBuilder.Entity<DeliveryAgent>();
			DeliveryAgentConfig.ToTable("DeliveryAgent");

			DeliveryAgentConfig.HasKey(x => x.Id);

			DeliveryAgentConfig.Property(x=>x.Id)
				.HasColumnName("Id")
				.IsRequired();

			DeliveryAgentConfig.Property(x=>x.Name)
				.HasColumnName("Name")
				.IsRequired();

			DeliveryAgentConfig.Property(x=>x.Account)
				.HasColumnName("Account")
				.IsOptional();

			DeliveryAgentConfig.Property(x=>x.DeliveryDiscount)
				.HasColumnName("DeliveryDiscount")
				.IsRequired();

			DeliveryAgentConfig.Property(x=>x.UpdateDate)
				.HasColumnName("UpdateDate")
				.IsRequired();

			DeliveryAgentConfig.Property(x=>x.CreateDate)
				.HasColumnName("CreateDate")
				.IsRequired();

			DeliveryAgentConfig.Property(x=>x.UpdateUserId)
				.HasColumnName("UpdateUserId")
				.IsRequired();

			DeliveryAgentConfig.HasRequired(x => x.UpdateUser)
				.WithMany(t=>t.DeliveryAgents)
				.HasForeignKey(x => x.UpdateUserId);

			#endregion DeliveryAgent

			#region DeliveryTarif

			var DeliveryTarifConfig = modelBuilder.Entity<DeliveryTarif>();
			DeliveryTarifConfig.ToTable("DeliveryTarif");

			DeliveryTarifConfig.HasKey(x => x.Id);

			DeliveryTarifConfig.Property(x=>x.Id)
				.HasColumnName("Id")
				.IsRequired();

			DeliveryTarifConfig.Property(x=>x.FromMiles)
				.HasColumnName("FromMiles")
				.IsRequired();

			DeliveryTarifConfig.Property(x=>x.ToMiles)
				.HasColumnName("ToMiles")
				.IsOptional();

			DeliveryTarifConfig.Property(x=>x.Price)
				.HasColumnName("Price")
				.IsRequired();

			DeliveryTarifConfig.Property(x=>x.DeliveryAgentId)
				.HasColumnName("DeliveryAgentId")
				.IsRequired();

			DeliveryTarifConfig.HasRequired(x => x.DeliveryAgent)
				.WithMany(t=>t.DeliveryTarifs)
				.HasForeignKey(x => x.DeliveryAgentId);

			DeliveryTarifConfig.Property(x=>x.UpdateDate)
				.HasColumnName("UpdateDate")
				.IsRequired();

			DeliveryTarifConfig.Property(x=>x.CreateDate)
				.HasColumnName("CreateDate")
				.IsRequired();

			DeliveryTarifConfig.Property(x=>x.UpdateUserId)
				.HasColumnName("UpdateUserId")
				.IsRequired();

			DeliveryTarifConfig.HasRequired(x => x.UpdateUser)
				.WithMany(t=>t.DeliveryTarifs)
				.HasForeignKey(x => x.UpdateUserId);

			#endregion DeliveryTarif

			#region Order

			var OrderConfig = modelBuilder.Entity<Order>();
			OrderConfig.ToTable("Order");

			OrderConfig.HasKey(x => x.OrderId);

			OrderConfig.Property(x=>x.OrderId)
				.HasColumnName("OrderId")
				.IsRequired();

			OrderConfig.Property(x=>x.Code)
				.HasColumnName("Code")
				.IsRequired();

			OrderConfig.Property(x=>x.EntityId)
				.HasColumnName("EntityId")
				.IsRequired();

			OrderConfig.HasRequired(x => x.Entity)
				.WithMany(t=>t.Orders)
				.HasForeignKey(x => x.EntityId);

			OrderConfig.Property(x=>x.ClientId)
				.HasColumnName("ClientId")
				.IsRequired();

			OrderConfig.HasRequired(x => x.Client)
				.WithMany(t=>t.Orders)
				.HasForeignKey(x => x.ClientId);

			OrderConfig.Property(x=>x.CreateDate)
				.HasColumnName("CreateDate")
				.IsRequired();

			OrderConfig.Property(x=>x.TotalAmount)
				.HasColumnName("TotalAmount")
				.IsRequired();

			OrderConfig.Property(x=>x.DeliveryCharge)
				.HasColumnName("DeliveryCharge")
				.IsRequired();

			OrderConfig.Property(x=>x.TaxAmount)
				.HasColumnName("TaxAmount")
				.IsRequired();

			OrderConfig.Property(x=>x.UpdateDate)
				.HasColumnName("UpdateDate")
				.IsRequired();

			OrderConfig.Property(x=>x.DeliveryAdress)
				.HasColumnName("DeliveryAdress")
				.IsOptional();

			OrderConfig.Property(x=>x.Phone)
				.HasColumnName("Phone")
				.IsOptional();

			OrderConfig.Property(x=>x.DeliveryLat)
				.HasColumnName("DeliveryLat")
				.IsOptional();

			OrderConfig.Property(x=>x.DeliveryLng)
				.HasColumnName("DeliveryLng")
				.IsOptional();

			OrderConfig.Property(x=>x.OrderStateId)
				.HasColumnName("OrderStateId")
				.IsRequired();

			OrderConfig.HasRequired(x => x.OrderState)
				.WithMany(t=>t.Orders)
				.HasForeignKey(x => x.OrderStateId);

			OrderConfig.Property(x=>x.OrderTypeId)
				.HasColumnName("OrderTypeId")
				.IsRequired();

			OrderConfig.HasRequired(x => x.OrderType)
				.WithMany(t=>t.Orders)
				.HasForeignKey(x => x.OrderTypeId);

			OrderConfig.Property(x=>x.PaymentRef)
				.HasColumnName("PaymentRef")
				.IsOptional();

			OrderConfig.Property(x=>x.ClientSignatureId)
				.HasColumnName("ClientSignatureId")
				.IsOptional();

			OrderConfig.HasOptional(x => x.ClientSignature)
				.WithMany(t=>t.Orders)
				.HasForeignKey(x => x.ClientSignatureId);

			OrderConfig.Property(x=>x.DeliveryTimeMinutes)
				.HasColumnName("DeliveryTimeMinutes")
				.IsOptional();

			OrderConfig.Property(x=>x.Remarks)
				.HasColumnName("Remarks")
				.IsOptional();

			OrderConfig.Property(x=>x.UpdateUserId)
				.HasColumnName("UpdateUserId")
				.IsOptional();

            OrderConfig.Property(x => x.ClientNotified)
                .HasColumnName("ClientNotified")
                .IsRequired();

            OrderConfig.Property(x => x.EntityNotified)
               .HasColumnName("EntityNotified")
               .IsRequired();

			OrderConfig.HasOptional(x => x.UpdateUser)
				.WithMany(t=>t.Orders)
				.HasForeignKey(x => x.UpdateUserId);

			#endregion Order

			#region Client

			var ClientConfig = modelBuilder.Entity<Client>();
			ClientConfig.ToTable("Client");

			ClientConfig.HasKey(x => x.Id);

			ClientConfig.Property(x=>x.Id)
				.HasColumnName("Id")
				.IsRequired();

			ClientConfig.Property(x=>x.Name)
				.HasColumnName("Name")
				.IsRequired();

			ClientConfig.Property(x=>x.Guid)
				.HasColumnName("Guid")
				.IsRequired();

			ClientConfig.Property(x=>x.LastName)
				.HasColumnName("LastName")
				.IsOptional();

			ClientConfig.Property(x=>x.LastName2)
				.HasColumnName("LastName2")
				.IsOptional();

			ClientConfig.Property(x=>x.AdressNo)
				.HasColumnName("AdressNo")
				.IsOptional();

			ClientConfig.Property(x=>x.AddressStreet)
				.HasColumnName("AddressStreet")
				.IsOptional();

			ClientConfig.Property(x=>x.Email)
				.HasColumnName("Email")
				.IsOptional();

			ClientConfig.Property(x=>x.Phone)
				.HasColumnName("Phone")
				.IsOptional();

			ClientConfig.Property(x=>x.Mobile)
				.HasColumnName("Mobile")
				.IsOptional();

			ClientConfig.Property(x=>x.Username)
				.HasColumnName("Username")
				.IsOptional();

			ClientConfig.Property(x=>x.Password)
				.HasColumnName("Password")
				.IsOptional();

			ClientConfig.Property(x=>x.IsRegistered)
				.HasColumnName("IsRegistered")
				.IsRequired();

			ClientConfig.Property(x=>x.Lat)
				.HasColumnName("Lat")
				.IsOptional();

			ClientConfig.Property(x=>x.Lng)
				.HasColumnName("Lng")
				.IsOptional();

			ClientConfig.Property(x=>x.CreateDate)
				.HasColumnName("CreateDate")
				.IsRequired();

			ClientConfig.Property(x=>x.UpdateDate)
				.HasColumnName("UpdateDate")
				.IsRequired();

			ClientConfig.Property(x=>x.DeviceId)
				.HasColumnName("DeviceId")
				.IsOptional();

			ClientConfig.Property(x=>x.SessionId)
				.HasColumnName("SessionId")
				.IsOptional();

			#endregion Client


		}

	}
}
