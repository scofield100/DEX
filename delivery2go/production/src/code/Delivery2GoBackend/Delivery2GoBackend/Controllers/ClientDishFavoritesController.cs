using System;
using System.Collections.Generic;
using System.Linq;
using System.Linq.Expressions;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using Enterlib;
using Enterlib.Parsing;
using Enterlib.Validation;
using Delivery2GoBackend.Models;

namespace Delivery2GoBackend.Controllers
{
	public class ClientDishFavoritesController : ApiController
	{

		deliveryContext context;
		QueryBuilder<ClientDishFavorites> queryBuider;

		public ClientDishFavoritesController()
		{
			context = new deliveryContext();
			queryBuider = new QueryBuilder<ClientDishFavorites>()
				.MapMember("ClientName", x => x.Client.Name)
				.MapMember("DishName", x => x.Dish.Name);
		}

		// GET api/clientdishfavorites/get/
		public System.Collections.ICollection Get(string filter = null, string orderby = null, int top = -1, int skip = -1)
		{
			if (orderby == null)
			{
				orderby = "ClientId,DishId";
			}
			return queryBuider.CreateQuery(context.ClientDishFavoriteses.AsNoTracking(), filter, orderby, skip, top, x => new
				{
					ClientId=x.ClientId,
					ClientName=x.Client.Name,
					DishId=x.DishId,
					DishName=x.Dish.Name,
				}).ToList();
		}

		// GET api/clientdishfavorites/get/5
		public object Get(int id)
		{
			return context.ClientDishFavoriteses.AsNoTracking()
				.Where(x=>x.ClientId == id)
				.Select(x=> new
				{
					ClientId=x.ClientId,
					ClientName=x.Client.Name,
					DishId=x.DishId,
					DishName=x.Dish.Name,
				})
			.FirstOrDefault();
		}

		// POST api/clientdishfavorites/
		public HttpResponseMessage Post([FromBody]ClientDishFavorites value)
		{
			ServerValidationInfo vi = null;
			if (!ModelState.IsValid)
			{
				vi = new ServerValidationInfo(ModelState);
			}
			if (vi != null && vi.ContainsError)
				return Request.CreateResponse(HttpStatusCode.BadRequest, vi);
			context.Entry(value).State = System.Data.EntityState.Added;
			context.SaveChanges();

			return Request.CreateResponse(HttpStatusCode.OK, value);
		}

		// PUT api/clientdishfavorites/
		public HttpResponseMessage Put([FromBody]ClientDishFavorites value, string filter = null)
		{
			ServerValidationInfo vi = null;
			if (!ModelState.IsValid)
			{
				vi = new ServerValidationInfo(ModelState);
			}
			if (filter == null)
			{
				context.Entry(value).State = System.Data.EntityState.Modified;
			}
			else
			{
				var old = context.ClientDishFavoriteses.SingleOrDefault(queryBuider.CreateWhere(filter));
				old.ClientId = value.ClientId;
				old.DishId = value.DishId;
			}
			if (vi != null && vi.ContainsError)
				return Request.CreateResponse(HttpStatusCode.BadRequest, vi);
			var result = context.SaveChanges() > 0;
			return Request.CreateResponse(HttpStatusCode.OK, result);
		}

		// DELETE api/clientdishfavorites/
		public bool Delete(string filter = null)
		{
			IQueryable<ClientDishFavorites> query = context.ClientDishFavoriteses;
			if (filter != null)
			{
				query = query.Where(queryBuider.CreateWhere(filter));
			}
			foreach (var item in query)
			{
				context.ClientDishFavoriteses.Remove(item);
			}
			return context.SaveChanges() > 0;
		}

		// GET api/clientdishfavorites/count
		[HttpGet]
		public int Count(string filter = null)
		{
			IQueryable<ClientDishFavorites> query = context.ClientDishFavoriteses.AsNoTracking();
			if (filter != null)
			{
				query = query.Where(queryBuider.CreateWhere(filter));
			}
			return query.Count();
		}

		// GET api/clientdishfavorites/find
		[HttpGet]
		public object Find(string filter)
		{
			return context.ClientDishFavoriteses.AsNoTracking()
				.Where(queryBuider.CreateWhere(filter))
				.Select(x=> new
				{
					ClientId=x.ClientId,
					ClientName=x.Client.Name,
					DishId=x.DishId,
					DishName=x.Dish.Name,
				})
			.FirstOrDefault();
		}

		protected override void Dispose(bool disposing)
		{
			if (disposing)
				context.Dispose();
			base.Dispose(disposing);
		}


	}
}
