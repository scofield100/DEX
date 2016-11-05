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
	public class DishController : ApiController
	{

		deliveryContext context;
		QueryBuilder<Dish> queryBuider;

		public DishController()
		{
			context = new deliveryContext();
			queryBuider = new QueryBuilder<Dish>()
				.MapMember("ImageLocation", x => x.Image.Location)
				.MapMember("EntityName", x => x.Entity.Name)
				.MapMember("MenuName", x => x.Menu.Name)
				.MapMember("UpdateUserName", x => x.UpdateUser.Name);
		}

		// GET api/dish/get/
		public System.Collections.ICollection Get(string filter = null, string orderby = null, int top = -1, int skip = -1)
		{
			if (orderby == null)
			{
				orderby = "Id";
			}
			return queryBuider.CreateQuery(context.Dishs.AsNoTracking(), filter, orderby, skip, top, x => new
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

		// GET api/dish/get/5
		public object Get(int id)
		{
			return context.Dishs.AsNoTracking()
				.Where(x=>x.Id == id)
				.Select(x=> new
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
				})
			.FirstOrDefault();
		}

		// POST api/dish/
		public HttpResponseMessage Post([FromBody]Dish value)
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
			context.Entry(value).State = System.Data.EntityState.Added;
			context.SaveChanges();

			return Request.CreateResponse(HttpStatusCode.OK, value);
		}

		// PUT api/dish/
		public HttpResponseMessage Put([FromBody]Dish value, string filter = null)
		{
			ServerValidationInfo vi = null;
			value.UpdateDate=DateTime.Now;
			value.CreateDate=DateTime.Now;
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
				var old = context.Dishs.SingleOrDefault(queryBuider.CreateWhere(filter));
				old.Id = value.Id;
				old.Name = value.Name;
				old.IsAvailable = value.IsAvailable;
				old.ImageId = value.ImageId;
				old.Ranking = value.Ranking;
				old.ReviewCount = value.ReviewCount;
				old.OrderCount = value.OrderCount;
				old.Price = value.Price;
				old.EntityId = value.EntityId;
				old.InWhatsGood = value.InWhatsGood;
				old.Description = value.Description;
				old.AvailableCount = value.AvailableCount;
				old.Tags = value.Tags;
				old.MenuId = value.MenuId;
				old.UpdateUserId = value.UpdateUserId;
				old.UpdateDate = value.UpdateDate;
				old.CreateDate = value.CreateDate;
			}
			if (vi != null && vi.ContainsError)
				return Request.CreateResponse(HttpStatusCode.BadRequest, vi);
			var result = context.SaveChanges() > 0;
			return Request.CreateResponse(HttpStatusCode.OK, result);
		}

		// DELETE api/dish/
		public bool Delete(string filter = null)
		{
			IQueryable<Dish> query = context.Dishs;
			if (filter != null)
			{
				query = query.Where(queryBuider.CreateWhere(filter));
			}
            
			foreach (var item in query.ToList())
			{
                foreach (var image in context.DishImageses
                    .Where(x=>x.DishId==item.Id)
                    .Select(x=>x.Image))
                {
                    context.Images.Remove(image);
                }

				context.Dishs.Remove(item);
			}
			return context.SaveChanges() > 0;
		}

		// GET api/dish/count
		[HttpGet]
		public int Count(string filter = null)
		{
			IQueryable<Dish> query = context.Dishs.AsNoTracking();
			if (filter != null)
			{
				query = query.Where(queryBuider.CreateWhere(filter));
			}
			return query.Count();
		}

		// GET api/dish/find
		[HttpGet]
		public object Find(string filter)
		{
			return context.Dishs.AsNoTracking()
				.Where(queryBuider.CreateWhere(filter))
				.Select(x=> new
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
