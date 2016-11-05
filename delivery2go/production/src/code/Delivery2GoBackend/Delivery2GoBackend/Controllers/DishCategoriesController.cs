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
	public class DishCategoriesController : ApiController
	{

		deliveryContext context;
		QueryBuilder<DishCategories> queryBuider;

		public DishCategoriesController()
		{
			context = new deliveryContext();
			queryBuider = new QueryBuilder<DishCategories>()
				.MapMember("DishName", x => x.Dish.Name)
				.MapMember("CategoryName", x => x.Category.Name);
		}

		// GET api/dishcategories/get/
		public System.Collections.ICollection Get(string filter = null, string orderby = null, int top = -1, int skip = -1)
		{
			if (orderby == null)
			{
				orderby = "DishId,CategoryId";
			}
			return queryBuider.CreateQuery(context.DishCategorieses.AsNoTracking(), filter, orderby, skip, top, x => new
				{
					DishId=x.DishId,
					DishName=x.Dish.Name,
					CategoryId=x.CategoryId,
					CategoryName=x.Category.Name,
				}).ToList();
		}

		// GET api/dishcategories/get/5
		public object Get(int id)
		{
			return context.DishCategorieses.AsNoTracking()
				.Where(x=>x.DishId == id)
				.Select(x=> new
				{
					DishId=x.DishId,
					DishName=x.Dish.Name,
					CategoryId=x.CategoryId,
					CategoryName=x.Category.Name,
				})
			.FirstOrDefault();
		}

		// POST api/dishcategories/
		public HttpResponseMessage Post([FromBody]DishCategories value)
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

		// PUT api/dishcategories/
		public HttpResponseMessage Put([FromBody]DishCategories value, string filter = null)
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
				var old = context.DishCategorieses.SingleOrDefault(queryBuider.CreateWhere(filter));
				old.DishId = value.DishId;
				old.CategoryId = value.CategoryId;
			}
			if (vi != null && vi.ContainsError)
				return Request.CreateResponse(HttpStatusCode.BadRequest, vi);
			var result = context.SaveChanges() > 0;
			return Request.CreateResponse(HttpStatusCode.OK, result);
		}

		// DELETE api/dishcategories/
		public bool Delete(string filter = null)
		{
			IQueryable<DishCategories> query = context.DishCategorieses;
			if (filter != null)
			{
				query = query.Where(queryBuider.CreateWhere(filter));
			}
			foreach (var item in query)
			{
				context.DishCategorieses.Remove(item);
			}
			return context.SaveChanges() > 0;
		}

		// GET api/dishcategories/count
		[HttpGet]
		public int Count(string filter = null)
		{
			IQueryable<DishCategories> query = context.DishCategorieses.AsNoTracking();
			if (filter != null)
			{
				query = query.Where(queryBuider.CreateWhere(filter));
			}
			return query.Count();
		}

		// GET api/dishcategories/find
		[HttpGet]
		public object Find(string filter)
		{
			return context.DishCategorieses.AsNoTracking()
				.Where(queryBuider.CreateWhere(filter))
				.Select(x=> new
				{
					DishId=x.DishId,
					DishName=x.Dish.Name,
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
