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
	public class EntityCategoriesController : ApiController
	{

		deliveryContext context;
		QueryBuilder<EntityCategories> queryBuider;

		public EntityCategoriesController()
		{
			context = new deliveryContext();
			queryBuider = new QueryBuilder<EntityCategories>()
				.MapMember("EntityName", x => x.Entity.Name)
				.MapMember("CategoryName", x => x.Category.Name);
		}

		// GET api/entitycategories/get/
		public System.Collections.ICollection Get(string filter = null, string orderby = null, int top = -1, int skip = -1)
		{
			if (orderby == null)
			{
				orderby = "EntityId,CategoryId";
			}
			return queryBuider.CreateQuery(context.EntityCategorieses.AsNoTracking(), filter, orderby, skip, top, x => new
				{
					EntityId=x.EntityId,
					EntityName=x.Entity.Name,
					CategoryId=x.CategoryId,
					CategoryName=x.Category.Name,
				}).ToList();
		}

		// GET api/entitycategories/get/5
		public object Get(int id)
		{
			return context.EntityCategorieses.AsNoTracking()
				.Where(x=>x.EntityId == id)
				.Select(x=> new
				{
					EntityId=x.EntityId,
					EntityName=x.Entity.Name,
					CategoryId=x.CategoryId,
					CategoryName=x.Category.Name,
				})
			.FirstOrDefault();
		}

		// POST api/entitycategories/
		public HttpResponseMessage Post([FromBody]EntityCategories value)
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

		// PUT api/entitycategories/
		public HttpResponseMessage Put([FromBody]EntityCategories value, string filter = null)
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
				var old = context.EntityCategorieses.SingleOrDefault(queryBuider.CreateWhere(filter));
				old.EntityId = value.EntityId;
				old.CategoryId = value.CategoryId;
			}
			if (vi != null && vi.ContainsError)
				return Request.CreateResponse(HttpStatusCode.BadRequest, vi);
			var result = context.SaveChanges() > 0;
			return Request.CreateResponse(HttpStatusCode.OK, result);
		}

		// DELETE api/entitycategories/
		public bool Delete(string filter = null)
		{
			IQueryable<EntityCategories> query = context.EntityCategorieses;
			if (filter != null)
			{
				query = query.Where(queryBuider.CreateWhere(filter));
			}
			foreach (var item in query)
			{
				context.EntityCategorieses.Remove(item);
			}
			return context.SaveChanges() > 0;
		}

		// GET api/entitycategories/count
		[HttpGet]
		public int Count(string filter = null)
		{
			IQueryable<EntityCategories> query = context.EntityCategorieses.AsNoTracking();
			if (filter != null)
			{
				query = query.Where(queryBuider.CreateWhere(filter));
			}
			return query.Count();
		}

		// GET api/entitycategories/find
		[HttpGet]
		public object Find(string filter)
		{
			return context.EntityCategorieses.AsNoTracking()
				.Where(queryBuider.CreateWhere(filter))
				.Select(x=> new
				{
					EntityId=x.EntityId,
					EntityName=x.Entity.Name,
					CategoryId=x.CategoryId,
					CategoryName=x.Category.Name,
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
