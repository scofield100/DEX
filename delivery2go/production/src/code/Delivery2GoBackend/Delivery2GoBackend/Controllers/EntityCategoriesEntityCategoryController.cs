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
	public class EntityCategoriesEntityCategoryController : ApiController
	{

		deliveryContext context;
		QueryBuilder<EntityCategories> queryBuider;
		QueryBuilder<EntityCategory> distintQueryBuider;

		public EntityCategoriesEntityCategoryController()
		{
			context = new deliveryContext();
			queryBuider = new QueryBuilder<EntityCategories>()
				.MapMember("Id", x => x.Category.Id)
				.MapMember("Code", x => x.Category.Code)
				.MapMember("Name", x => x.Category.Name);

			distintQueryBuider = new QueryBuilder<EntityCategory>()
;
		}

		// GET api/entitycategoriesentitycategory/get/
		public System.Collections.ICollection Get(int EntityId, string filter = null, string orderby = null, int top = -1, int skip = -1, bool distint=false)
		{
			if (orderby == null)
			{
				orderby = "Id";
			}
			if(!distint)
			{
				return queryBuider.CreateQuery(context.EntityCategorieses.AsNoTracking().Where(x=>x.EntityId == EntityId), filter, orderby, skip, top, x => new
					{
						Id=x.Category.Id,
						Code=x.Category.Code,
						Name=x.Category.Name,
				}).ToList();
			}
			else
			{
				return distintQueryBuider.CreateQuery(context.EntityCategories.AsNoTracking().Where(x => !x.EntityCategorieses.Any(y => y.EntityId == EntityId)), filter, orderby, skip, top, x => new
					{
						Id=x.Id,
						Code=x.Code,
						Name=x.Name,
				}).ToList();
			}
		}

		// GET api/EntityCategoriesEntityCategory/get/5
		public object Get(int id, int EntityId)
		{
			return context.EntityCategorieses.AsNoTracking().Where(x=>x.EntityId == EntityId && x.Category.Id == id)
				.Select(x=> new
				{
					Id=x.Category.Id,
					Code=x.Category.Code,
					Name=x.Category.Name,
				})
			.FirstOrDefault();
		}

		// POST api/entitycategories/
		public HttpResponseMessage Post(int EntityId, [FromBody]EntityCategory value)
		{
			ServerValidationInfo vi = null;
			if (!ModelState.IsValid)
			{
				vi = new ServerValidationInfo(ModelState);
			}
			if (vi != null && vi.ContainsError)
				return Request.CreateResponse(HttpStatusCode.BadRequest, vi);
			var rel = new EntityCategories(){ EntityId=EntityId, Category=value };
			context.Entry(rel).State = System.Data.EntityState.Added;
			context.SaveChanges();
			value.EntityCategorieses= null;

			return Request.CreateResponse(HttpStatusCode.OK, value);
		}

		// PUT api/entitycategoriesentitycategory/
		public HttpResponseMessage Put([FromBody]EntityCategory value)
		{
			ServerValidationInfo vi = null;
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

		// DELETE api/entitycategoriesentitycategory/
		public bool Delete(int EntityId, string filter = null)
		{
			IQueryable<EntityCategories> query = context.EntityCategorieses.Where(x=>x.EntityId == EntityId);
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

		// GET api/entitycategoriesentitycategory/count
		[HttpGet]
		public int Count(int EntityId,string filter = null, bool distint=false)
		{
			if(!distint)
			{
				var query = context.EntityCategorieses.AsNoTracking().Where(x=>x.EntityId == EntityId);
				if (filter != null)
				{
					query = query.Where(queryBuider.CreateWhere(filter));
				}
				return query.Count();
			}
			else
			{
				var query = context.EntityCategories.AsNoTracking().Where(x => !x.EntityCategorieses.Any(y => y.EntityId == EntityId));
				if (filter != null)
				{
					query = query.Where(distintQueryBuider.CreateWhere(filter));
				}
				return query.Count();
			}
		}

		// GET api/entitycategories/find
		[HttpGet]
		public object Find(string filter)
		{
			return context.EntityCategorieses.AsNoTracking()
				.Where(queryBuider.CreateWhere(filter))
				.Select(x=> new
				{
					Id=x.Category.Id,
					Code=x.Category.Code,
					Name=x.Category.Name,
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
