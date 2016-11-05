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
	public class ClientDishFavoritesDishController : ApiController
	{

		deliveryContext context;
		QueryBuilder<ClientDishFavorites> queryBuider;
		QueryBuilder<Dish> distintQueryBuider;

		public ClientDishFavoritesDishController()
		{
			context = new deliveryContext();
			queryBuider = new QueryBuilder<ClientDishFavorites>()
				.MapMember("Id", x => x.Dish.Id)
				.MapMember("Name", x => x.Dish.Name)
				.MapMember("IsAvailable", x => x.Dish.IsAvailable)
				.MapMember("ImageId", x => x.Dish.ImageId)
				.MapMember("Ranking", x => x.Dish.Ranking)
				.MapMember("ReviewCount", x => x.Dish.ReviewCount)
				.MapMember("OrderCount", x => x.Dish.OrderCount)
				.MapMember("Price", x => x.Dish.Price)
				.MapMember("EntityId", x => x.Dish.EntityId)
				.MapMember("InWhatsGood", x => x.Dish.InWhatsGood)
				.MapMember("Description", x => x.Dish.Description)
				.MapMember("AvailableCount", x => x.Dish.AvailableCount)
				.MapMember("Tags", x => x.Dish.Tags)
				.MapMember("MenuId", x => x.Dish.MenuId)
				.MapMember("UpdateUserId", x => x.Dish.UpdateUserId)
				.MapMember("UpdateDate", x => x.Dish.UpdateDate)
				.MapMember("CreateDate", x => x.Dish.CreateDate)
				.MapMember("ImageLocation", x => x.Dish.Image.Location)
				.MapMember("EntityName", x => x.Dish.Entity.Name)
				.MapMember("MenuName", x => x.Dish.Menu.Name)
				.MapMember("UpdateUserName", x => x.Dish.UpdateUser.Name);

			distintQueryBuider = new QueryBuilder<Dish>()
				.MapMember("ImageLocation", x => x.Image.Location)
				.MapMember("EntityName", x => x.Entity.Name)
				.MapMember("MenuName", x => x.Menu.Name)
				.MapMember("UpdateUserName", x => x.UpdateUser.Name);
		}

		// GET api/clientdishfavoritesdish/get/
		public System.Collections.ICollection Get(int ClientId, string filter = null, string orderby = null, int top = -1, int skip = -1, bool distint=false)
		{
			if (orderby == null)
			{
				orderby = "Id";
			}
			if(!distint)
			{
				return queryBuider.CreateQuery(context.ClientDishFavoriteses.AsNoTracking().Where(x=>x.ClientId == ClientId), filter, orderby, skip, top, x => new
					{
						Id=x.Dish.Id,
						Name=x.Dish.Name,
						IsAvailable=x.Dish.IsAvailable,
						ImageId=x.Dish.ImageId,
						ImageLocation=x.Dish.Image.Location,
						Ranking=x.Dish.Ranking,
						ReviewCount=x.Dish.ReviewCount,
						OrderCount=x.Dish.OrderCount,
						Price=x.Dish.Price,
						EntityId=x.Dish.EntityId,
						EntityName=x.Dish.Entity.Name,
						InWhatsGood=x.Dish.InWhatsGood,
						Description=x.Dish.Description,
						AvailableCount=x.Dish.AvailableCount,
						Tags=x.Dish.Tags,
						MenuId=x.Dish.MenuId,
						MenuName=x.Dish.Menu.Name,
						UpdateUserId=x.Dish.UpdateUserId,
						UpdateUserName=x.Dish.UpdateUser.Name,
						UpdateDate=x.Dish.UpdateDate,
						CreateDate=x.Dish.CreateDate,
				}).ToList();
			}
			else
			{
				return distintQueryBuider.CreateQuery(context.Dishs.AsNoTracking().Where(x => !x.ClientDishFavoriteses.Any(y => y.ClientId == ClientId)), filter, orderby, skip, top, x => new
					{
						Id=x.Id,
						Name=x.Name,
						IsAvailable=x.IsAvailable,
						ImageId=x.ImageId,
						ImageLocation=x.Image.Location,
						Ranking=x.Ranking,
						ReviewCount=x.ReviewCount,
						OrderCount=x.OrderCount,
						Price=x.Price,
						EntityId=x.EntityId,
						EntityName=x.Entity.Name,
						InWhatsGood=x.InWhatsGood,
						Description=x.Description,
						AvailableCount=x.AvailableCount,
						Tags=x.Tags,
						MenuId=x.MenuId,
						MenuName=x.Menu.Name,
						UpdateUserId=x.UpdateUserId,
						UpdateUserName=x.UpdateUser.Name,
						UpdateDate=x.UpdateDate,
						CreateDate=x.CreateDate,
				}).ToList();
			}
		}

		// GET api/ClientDishFavoritesDish/get/5
		public object Get(int id, int ClientId)
		{
			return context.ClientDishFavoriteses.AsNoTracking().Where(x=>x.ClientId == ClientId && x.Dish.Id == id)
				.Select(x=> new
				{
					Id=x.Dish.Id,
					Name=x.Dish.Name,
					IsAvailable=x.Dish.IsAvailable,
					ImageId=x.Dish.ImageId,
					ImageLocation=x.Dish.Image.Location,
					Ranking=x.Dish.Ranking,
					ReviewCount=x.Dish.ReviewCount,
					OrderCount=x.Dish.OrderCount,
					Price=x.Dish.Price,
					EntityId=x.Dish.EntityId,
					EntityName=x.Dish.Entity.Name,
					InWhatsGood=x.Dish.InWhatsGood,
					Description=x.Dish.Description,
					AvailableCount=x.Dish.AvailableCount,
					Tags=x.Dish.Tags,
					MenuId=x.Dish.MenuId,
					MenuName=x.Dish.Menu.Name,
					UpdateUserId=x.Dish.UpdateUserId,
					UpdateUserName=x.Dish.UpdateUser.Name,
					UpdateDate=x.Dish.UpdateDate,
					CreateDate=x.Dish.CreateDate,
				})
			.FirstOrDefault();
		}

		// POST api/clientdishfavorites/
		public HttpResponseMessage Post(int ClientId, [FromBody]Dish value)
		{
			ServerValidationInfo vi = null;
			value.UpdateDate=DateTime.Now;
			value.CreateDate=DateTime.Now;
			if (!ModelState.IsValid)
			{
				vi = new ServerValidationInfo(ModelState);
			}
			if (vi != null && vi.ContainsError)
				return Request.CreateResponse(HttpStatusCode.BadRequest, vi);
			var rel = new ClientDishFavorites(){ ClientId=ClientId, Dish=value };
			context.Entry(rel).State = System.Data.EntityState.Added;
			context.SaveChanges();
			value.ClientDishFavoriteses= null;

			return Request.CreateResponse(HttpStatusCode.OK, value);
		}

		// PUT api/clientdishfavoritesdish/
		public HttpResponseMessage Put([FromBody]Dish value)
		{
			ServerValidationInfo vi = null;
			value.UpdateDate=DateTime.Now;
			value.CreateDate=DateTime.Now;
			if (!ModelState.IsValid)
			{
				vi = new ServerValidationInfo(ModelState);
			}
			if (vi != null && vi.ContainsError)
				return Request.CreateResponse(HttpStatusCode.BadRequest, vi);
			context.Entry(value).State = System.Data.EntityState.Modified;
			var result = context.SaveChanges() > 0;
			return Request.CreateResponse(HttpStatusCode.OK, result);
		}

		// DELETE api/clientdishfavoritesdish/
		public bool Delete(int ClientId, string filter = null)
		{
			IQueryable<ClientDishFavorites> query = context.ClientDishFavoriteses.Where(x=>x.ClientId == ClientId);
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

		// GET api/clientdishfavoritesdish/count
		[HttpGet]
		public int Count(int ClientId,string filter = null, bool distint=false)
		{
			if(!distint)
			{
				var query = context.ClientDishFavoriteses.AsNoTracking().Where(x=>x.ClientId == ClientId);
				if (filter != null)
				{
					query = query.Where(queryBuider.CreateWhere(filter));
				}
				return query.Count();
			}
			else
			{
				var query = context.Dishs.AsNoTracking().Where(x => !x.ClientDishFavoriteses.Any(y => y.ClientId == ClientId));
				if (filter != null)
				{
					query = query.Where(distintQueryBuider.CreateWhere(filter));
				}
				return query.Count();
			}
		}

		// GET api/clientdishfavorites/find
		[HttpGet]
		public object Find(string filter)
		{
			return context.ClientDishFavoriteses.AsNoTracking()
				.Where(queryBuider.CreateWhere(filter))
				.Select(x=> new
				{
					Id=x.Dish.Id,
					Name=x.Dish.Name,
					IsAvailable=x.Dish.IsAvailable,
					ImageId=x.Dish.ImageId,
					ImageLocation=x.Dish.Image.Location,
					Ranking=x.Dish.Ranking,
					ReviewCount=x.Dish.ReviewCount,
					OrderCount=x.Dish.OrderCount,
					Price=x.Dish.Price,
					EntityId=x.Dish.EntityId,
					EntityName=x.Dish.Entity.Name,
					InWhatsGood=x.Dish.InWhatsGood,
					Description=x.Dish.Description,
					AvailableCount=x.Dish.AvailableCount,
					Tags=x.Dish.Tags,
					MenuId=x.Dish.MenuId,
					MenuName=x.Dish.Menu.Name,
					UpdateUserId=x.Dish.UpdateUserId,
					UpdateUserName=x.Dish.UpdateUser.Name,
					UpdateDate=x.Dish.UpdateDate,
					CreateDate=x.Dish.CreateDate,
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
