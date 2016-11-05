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
	public class EntityMenuController : ApiController
	{

		deliveryContext context;
		QueryBuilder<EntityMenu> queryBuider;

		public EntityMenuController()
		{
			context = new deliveryContext();
			queryBuider = new QueryBuilder<EntityMenu>()
				.MapMember("EntityName", x => x.Entity.Name)
				.MapMember("ImageLocation", x => x.Image.Location)
				.MapMember("UpdateUserName", x => x.UpdateUser.Name);
		}

		// GET api/entitymenu/get/
		public System.Collections.ICollection Get(string filter = null, string orderby = null, int top = -1, int skip = -1)
		{
			if (orderby == null)
			{
				orderby = "Id";
			}
			return queryBuider.CreateQuery(context.EntityMenus.AsNoTracking(), filter, orderby, skip, top, x => new
				{
					Id=x.Id,
					Name=x.Name,
					EntityId=x.EntityId,
					EntityName=x.Entity.Name,
					IsSpecial=x.IsSpecial,
					IsAvailable=x.IsAvailable,
					ImageId=x.ImageId,
					ImageLocation=x.Image.Location,
					UpdateUserId=x.UpdateUserId,
					UpdateUserName=x.UpdateUser.Name,
					UpdateDate=x.UpdateDate,
					CreateDate=x.CreateDate,
				}).ToList();
		}

		// GET api/entitymenu/get/5
		public object Get(int id)
		{
			return context.EntityMenus.AsNoTracking()
				.Where(x=>x.Id == id)
				.Select(x=> new
				{
					Id=x.Id,
					Name=x.Name,
					EntityId=x.EntityId,
					EntityName=x.Entity.Name,
					IsSpecial=x.IsSpecial,
					IsAvailable=x.IsAvailable,
					ImageId=x.ImageId,
					ImageLocation=x.Image.Location,
					UpdateUserId=x.UpdateUserId,
					UpdateUserName=x.UpdateUser.Name,
					UpdateDate=x.UpdateDate,
					CreateDate=x.CreateDate,
				})
			.FirstOrDefault();
		}

		// POST api/entitymenu/
		public HttpResponseMessage Post([FromBody]EntityMenu value)
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

		// PUT api/entitymenu/
		public HttpResponseMessage Put([FromBody]EntityMenu value, string filter = null)
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
				var old = context.EntityMenus.SingleOrDefault(queryBuider.CreateWhere(filter));
				old.Id = value.Id;
				old.Name = value.Name;
				old.EntityId = value.EntityId;
				old.IsSpecial = value.IsSpecial;
				old.IsAvailable = value.IsAvailable;
				old.ImageId = value.ImageId;
				old.UpdateUserId = value.UpdateUserId;
				old.UpdateDate = value.UpdateDate;
				old.CreateDate = value.CreateDate;
			}
			if (vi != null && vi.ContainsError)
				return Request.CreateResponse(HttpStatusCode.BadRequest, vi);
			var result = context.SaveChanges() > 0;
			return Request.CreateResponse(HttpStatusCode.OK, result);
		}

		// DELETE api/entitymenu/
		public bool Delete(string filter = null)
		{
			IQueryable<EntityMenu> query = context.EntityMenus;
			if (filter != null)
			{
				query = query.Where(queryBuider.CreateWhere(filter));
			}
			foreach (var item in query)
			{
				context.EntityMenus.Remove(item);
			}
			return context.SaveChanges() > 0;
		}

		// GET api/entitymenu/count
		[HttpGet]
		public int Count(string filter = null)
		{
			IQueryable<EntityMenu> query = context.EntityMenus.AsNoTracking();
			if (filter != null)
			{
				query = query.Where(queryBuider.CreateWhere(filter));
			}
			return query.Count();
		}

		// GET api/entitymenu/find
		[HttpGet]
		public object Find(string filter)
		{
			return context.EntityMenus.AsNoTracking()
				.Where(queryBuider.CreateWhere(filter))
				.Select(x=> new
				{
					Id=x.Id,
					Name=x.Name,
					EntityId=x.EntityId,
					EntityName=x.Entity.Name,
					IsSpecial=x.IsSpecial,
					IsAvailable=x.IsAvailable,
					ImageId=x.ImageId,
					ImageLocation=x.Image.Location,
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
