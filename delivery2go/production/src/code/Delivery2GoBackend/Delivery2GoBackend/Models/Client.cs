using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.ComponentModel.DataAnnotations;

namespace Delivery2GoBackend.Models
{
	public partial class Client
	{

		public Client()
		{

			ClientDishFavoriteses = new HashSet<ClientDishFavorites>();
			ClientEntityFavoriteses = new HashSet<ClientEntityFavorites>();
			EntityReviews = new HashSet<EntityReview>();
			DishReviews = new HashSet<DishReview>();
			Orders = new HashSet<Order>();

		}

		public int Id { get; set; }

		[Required]
		public string Name { get; set; }

		[Required]
		public string Guid { get; set; }

		public string LastName { get; set; }

		public string LastName2 { get; set; }

		public string AdressNo { get; set; }

		public string AddressStreet { get; set; }

		public string Email { get; set; }

		public string Phone { get; set; }

		public string Mobile { get; set; }

		public string Username { get; set; }

		public string Password { get; set; }

		public bool IsRegistered { get; set; }

		public double? Lat { get; set; }

		public double? Lng { get; set; }

		public DateTime CreateDate { get; set; }

		public DateTime UpdateDate { get; set; }

		public string DeviceId { get; set; }

		public string SessionId { get; set; }

		public virtual ICollection<ClientDishFavorites> ClientDishFavoriteses {get;set;}

		public virtual ICollection<ClientEntityFavorites> ClientEntityFavoriteses {get;set;}

		public virtual ICollection<EntityReview> EntityReviews {get;set;}

		public virtual ICollection<DishReview> DishReviews {get;set;}

		public virtual ICollection<Order> Orders {get;set;}

	}
}
