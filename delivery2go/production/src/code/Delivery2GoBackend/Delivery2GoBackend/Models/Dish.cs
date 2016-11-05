using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.ComponentModel.DataAnnotations;

namespace Delivery2GoBackend.Models
{
	public partial class Dish
	{

		public Dish()
		{

			DishCategorieses = new HashSet<DishCategories>();
			DishImageses = new HashSet<DishImages>();
			ClientDishFavoriteses = new HashSet<ClientDishFavorites>();
			DishSizePrices = new HashSet<DishSizePrice>();
			OrderDishs = new HashSet<OrderDish>();
			DishReviews = new HashSet<DishReview>();

		}

		public int Id { get; set; }

		[Required]
		public string Name { get; set; }

		public bool IsAvailable { get; set; }

		public int? ImageId { get; set; }

		public int? Ranking { get; set; }

		public int? ReviewCount { get; set; }

		public int OrderCount { get; set; }

		public double Price { get; set; }

		public int EntityId { get; set; }

		public bool InWhatsGood { get; set; }

		public string Description { get; set; }

		public int? AvailableCount { get; set; }

		public string Tags { get; set; }

		public int? MenuId { get; set; }

		public int? UpdateUserId { get; set; }

		public DateTime UpdateDate { get; set; }

		public DateTime CreateDate { get; set; }

		public virtual Image Image { get; set; }

		public virtual Entity Entity { get; set; }

		public virtual EntityMenu Menu { get; set; }

		public virtual User UpdateUser { get; set; }

		public virtual ICollection<DishCategories> DishCategorieses {get;set;}

		public virtual ICollection<DishImages> DishImageses {get;set;}

		public virtual ICollection<ClientDishFavorites> ClientDishFavoriteses {get;set;}

		public virtual ICollection<DishSizePrice> DishSizePrices {get;set;}

		public virtual ICollection<OrderDish> OrderDishs {get;set;}

		public virtual ICollection<DishReview> DishReviews {get;set;}

	}
}
