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
	public class AddonsController : ApiController
	{

		deliveryContext context;
		QueryBuilder<Addons> queryBuider;

		public AddonsController()
		{
			context = new deliveryContext();
			queryBuider = new QueryBuilder<Addons>()
				.MapMember("EntityName", x => x.Entity.Name)
				.MapMember("UpdateUserName", x => x.UpdateUser.Name);
		}

		// GET api/addons/get/
		public System.Collections.ICollection Get(string filter = null, string orderby = null, int top = -1, int skip = -1)
		{
			if (orderby == null)
			{
				orderby = "Id";
			}
			return queryBuider.CreateQuery(context.Addonses.AsNoTracking(), filter, orderby, skip, top, x => new
				{
					Id=x.Id,
					Name=x.Name,
					EntityId=x.EntityId,
					EntityName=x.Entity.Name,
					Price=x.Price,
					IsAvailable=x.IsAvailable,
					IsDressing=x.IsDressing,
					AvailableCount=x.AvailableCount,
					UpdateUserId=x.UpdateUserId,
					UpdateUserName=x.UpdateUser.Name,
					UpdateDate=x.UpdateDate,
					CreateDate=x.CreateDate,
				}).ToList();
		}

		// GET api/addons/get/5
		public object Get(int id)
		{
			return context.Addonses.AsNoTracking()
				.Where(x=>x.Id == id)
				.Select(x=> new
				{
					Id=x.Id,
					Name=x.Name,
					EntityId=x.EntityId,
					EntityName=x.Entity.Name,
					Price=x.Price,
					IsAvailable=x.IsAvailable,
					IsDressing=x.IsDressing,
					AvailableCount=x.AvailableCount,
					UpdateUserId=x.UpdateUserId,
					UpdateUserName=x.UpdateUser.Name,
					UpdateDate=x.UpdateDate,
					CreateDate=x.CreateDate,
				})
			.FirstOrDefault();
		}

		// POST api/addons/
		public HttpResponseMessage Post([FromBody]Addons value)
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

		// PUT api/addons/
		public HttpResponseMessage Put([FromBody]Addons value, string filter = null)
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
				var old = context.Addonses.SingleOrDefault(queryBuider.CreateWhere(filter));
				old.Id = value.Id;
				old.Name = value.Name;
				old.EntityId = value.EntityId;
				old.Price = value.Price;
				old.IsAvailable = value.IsAvailable;
				old.IsDressing = value.IsDressing;
				old.AvailableCount = value.AvailableCount;
				old.UpdateUserId = value.UpdateUserId;
				old.UpdateDate = value.UpdateDate;
				old.CreateDate = value.CreateDate;
			}
			if (vi != null && vi.ContainsError)
				return Request.CreateResponse(HttpStatusCode.BadRequest, vi);
			var result = context.SaveChanges() > 0;
			return Request.CreateResponse(HttpStatusCode.OK, result);
		}

		// DELETE api/addons/
		public bool Delete(string filter = null)
		{
			IQueryable<Addons> query = context.Addonses;
			if (filter != null)
			{
				query = query.Where(queryBuider.CreateWhere(filter));
			}
			foreach (var item in query)
			{
				context.Addonses.Remove(item);
			}
			return context.SaveChanges() > 0;
		}

		// GET api/addons/count
		[HttpGet]
		public int Count(string filter = null)
		{
			IQueryable<Addons> query = context.Addonses.AsNoTracking();
			if (filter != null)
			{
				query = query.Where(queryBuider.CreateWhere(filter));
			}
			return query.Count();
		}

		// GET api/addons/find
		[HttpGet]
		public object Find(string filter)
		{
			return context.Addonses.AsNoTracking()
				.Where(queryBuider.CreateWhere(filter))
				.Select(x=> new
				{
					Id=x.Id,
					Name=x.Name,
					EntityId=x.EntityId,
					EntityName=x.Entity.Name,
					Price=x.Price,
					IsAvailable=x.IsAvailable,
					IsDressing=x.IsDressing,
					AvailableCount=x.AvailableCount,
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
