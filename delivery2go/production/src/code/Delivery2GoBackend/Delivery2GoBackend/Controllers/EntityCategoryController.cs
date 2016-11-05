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
	public class EntityCategoryController : ApiController
	{

		deliveryContext context;
		QueryBuilder<EntityCategory> queryBuider;

		public EntityCategoryController()
		{
			context = new deliveryContext();
			queryBuider = new QueryBuilder<EntityCategory>();
		}

		// GET api/entitycategory/get/
		public System.Collections.ICollection Get(string filter = null, string orderby = null, int top = -1, int skip = -1)
		{
			if (orderby == null)
			{
				orderby = "Id";
			}
			return queryBuider.CreateQuery(context.EntityCategories.AsNoTracking(), filter, orderby, skip, top).ToList();
		}

		// GET api/entitycategory/get/5
		public object Get(int id)
		{
			return context.EntityCategories.AsNoTracking()
				.Where(x=>x.Id == id)
			.FirstOrDefault();
		}

		// POST api/entitycategory/
		public HttpResponseMessage Post([FromBody]EntityCategory value)
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

		// PUT api/entitycategory/
		public HttpResponseMessage Put([FromBody]EntityCategory value, string filter = null)
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
				var old = context.EntityCategories.SingleOrDefault(queryBuider.CreateWhere(filter));
				old.Id = value.Id;
				old.Code = value.Code;
				old.Name = value.Name;
			}
			if (vi != null && vi.ContainsError)
				return Request.CreateResponse(HttpStatusCode.BadRequest, vi);
			var result = context.SaveChanges() > 0;
			return Request.CreateResponse(HttpStatusCode.OK, result);
		}

		// DELETE api/entitycategory/
		public bool Delete(string filter = null)
		{
			IQueryable<EntityCategory> query = context.EntityCategories;
			if (filter != null)
			{
				query = query.Where(queryBuider.CreateWhere(filter));
			}
			foreach (var item in query)
			{
				context.EntityCategories.Remove(item);
			}
			return context.SaveChanges() > 0;
		}

		// GET api/entitycategory/count
		[HttpGet]
		public int Count(string filter = null)
		{
			IQueryable<EntityCategory> query = context.EntityCategories.AsNoTracking();
			if (filter != null)
			{
				query = query.Where(queryBuider.CreateWhere(filter));
			}
			return query.Count();
		}

		// GET api/entitycategory/find
		[HttpGet]
		public object Find(string filter)
		{
			return context.EntityCategories.AsNoTracking()
				.Where(queryBuider.CreateWhere(filter))
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
