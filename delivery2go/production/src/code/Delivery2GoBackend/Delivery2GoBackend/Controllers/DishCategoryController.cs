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
	public class DishCategoryController : ApiController
	{

		deliveryContext context;
		QueryBuilder<DishCategory> queryBuider;

		public DishCategoryController()
		{
			context = new deliveryContext();
			queryBuider = new QueryBuilder<DishCategory>()
				.MapMember("UpdateUserName", x => x.UpdateUser.Name);
		}

		// GET api/dishcategory/get/
		public System.Collections.ICollection Get(string filter = null, string orderby = null, int top = -1, int skip = -1)
		{
			if (orderby == null)
			{
				orderby = "Id";
			}
			return queryBuider.CreateQuery(context.DishCategories.AsNoTracking(), filter, orderby, skip, top, x => new
				{
					Id=x.Id,
					Code=x.Code,
					Name=x.Name,
					UpdateUserId=x.UpdateUserId,
					UpdateUserName=x.UpdateUser.Name,
					UpdateDate=x.UpdateDate,
				}).ToList();
		}

		// GET api/dishcategory/get/5
		public object Get(int id)
		{
			return context.DishCategories.AsNoTracking()
				.Where(x=>x.Id == id)
				.Select(x=> new
				{
					Id=x.Id,
					Code=x.Code,
					Name=x.Name,
					UpdateUserId=x.UpdateUserId,
					UpdateUserName=x.UpdateUser.Name,
					UpdateDate=x.UpdateDate,
				})
			.FirstOrDefault();
		}

		// POST api/dishcategory/
		public HttpResponseMessage Post([FromBody]DishCategory value)
		{
			ServerValidationInfo vi = null;
			value.UpdateDate=DateTime.Now;
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

		// PUT api/dishcategory/
		public HttpResponseMessage Put([FromBody]DishCategory value, string filter = null)
		{
			ServerValidationInfo vi = null;
			value.UpdateDate=DateTime.Now;
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
				var old = context.DishCategories.SingleOrDefault(queryBuider.CreateWhere(filter));
				old.Id = value.Id;
				old.Code = value.Code;
				old.Name = value.Name;
				old.UpdateUserId = value.UpdateUserId;
				old.UpdateDate = value.UpdateDate;
			}
			if (vi != null && vi.ContainsError)
				return Request.CreateResponse(HttpStatusCode.BadRequest, vi);
			var result = context.SaveChanges() > 0;
			return Request.CreateResponse(HttpStatusCode.OK, result);
		}

		// DELETE api/dishcategory/
		public bool Delete(string filter = null)
		{
			IQueryable<DishCategory> query = context.DishCategories;
			if (filter != null)
			{
				query = query.Where(queryBuider.CreateWhere(filter));
			}
			foreach (var item in query)
			{
				context.DishCategories.Remove(item);
			}
			return context.SaveChanges() > 0;
		}

		// GET api/dishcategory/count
		[HttpGet]
		public int Count(string filter = null)
		{
			IQueryable<DishCategory> query = context.DishCategories.AsNoTracking();
			if (filter != null)
			{
				query = query.Where(queryBuider.CreateWhere(filter));
			}
			return query.Count();
		}

		// GET api/dishcategory/find
		[HttpGet]
		public object Find(string filter)
		{
			return context.DishCategories.AsNoTracking()
				.Where(queryBuider.CreateWhere(filter))
				.Select(x=> new
				{
					Id=x.Id,
					Code=x.Code,
					Name=x.Name,
					UpdateUserId=x.UpdateUserId,
					UpdateUserName=x.UpdateUser.Name,
					UpdateDate=x.UpdateDate,
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
