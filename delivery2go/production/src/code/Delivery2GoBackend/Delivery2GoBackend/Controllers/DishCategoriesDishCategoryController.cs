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
	public class DishCategoriesDishCategoryController : ApiController
	{

		deliveryContext context;
		QueryBuilder<DishCategories> queryBuider;
		QueryBuilder<DishCategory> distintQueryBuider;

		public DishCategoriesDishCategoryController()
		{
			context = new deliveryContext();
			queryBuider = new QueryBuilder<DishCategories>()
				.MapMember("Id", x => x.Category.Id)
				.MapMember("Code", x => x.Category.Code)
				.MapMember("Name", x => x.Category.Name)
				.MapMember("UpdateUserId", x => x.Category.UpdateUserId)
				.MapMember("UpdateDate", x => x.Category.UpdateDate)
				.MapMember("UpdateUserName", x => x.Category.UpdateUser.Name);

			distintQueryBuider = new QueryBuilder<DishCategory>()
				.MapMember("UpdateUserName", x => x.UpdateUser.Name);
		}

		// GET api/dishcategoriesdishcategory/get/
		public System.Collections.ICollection Get(int DishId, string filter = null, string orderby = null, int top = -1, int skip = -1, bool distint=false)
		{
			if (orderby == null)
			{
				orderby = "Id";
			}
			if(!distint)
			{
				return queryBuider.CreateQuery(context.DishCategorieses.AsNoTracking().Where(x=>x.DishId == DishId), filter, orderby, skip, top, x => new
					{
						Id=x.Category.Id,
						Code=x.Category.Code,
						Name=x.Category.Name,
						UpdateUserId=x.Category.UpdateUserId,
						UpdateUserName=x.Category.UpdateUser.Name,
						UpdateDate=x.Category.UpdateDate,
				}).ToList();
			}
			else
			{
				return distintQueryBuider.CreateQuery(context.DishCategories.AsNoTracking().Where(x => !x.DishCategorieses.Any(y => y.DishId == DishId)), filter, orderby, skip, top, x => new
					{
						Id=x.Id,
						Code=x.Code,
						Name=x.Name,
						UpdateUserId=x.UpdateUserId,
						UpdateUserName=x.UpdateUser.Name,
						UpdateDate=x.UpdateDate,
				}).ToList();
			}
		}

		// GET api/DishCategoriesDishCategory/get/5
		public object Get(int id, int DishId)
		{
			return context.DishCategorieses.AsNoTracking().Where(x=>x.DishId == DishId && x.Category.Id == id)
				.Select(x=> new
				{
					Id=x.Category.Id,
					Code=x.Category.Code,
					Name=x.Category.Name,
					UpdateUserId=x.Category.UpdateUserId,
					UpdateUserName=x.Category.UpdateUser.Name,
					UpdateDate=x.Category.UpdateDate,
				})
			.FirstOrDefault();
		}

		// POST api/dishcategories/
		public HttpResponseMessage Post(int DishId, [FromBody]DishCategory value)
		{
			ServerValidationInfo vi = null;
			value.UpdateDate=DateTime.Now;
			if (!ModelState.IsValid)
			{
				vi = new ServerValidationInfo(ModelState);
			}
			if (vi != null && vi.ContainsError)
				return Request.CreateResponse(HttpStatusCode.BadRequest, vi);
			var rel = new DishCategories(){ DishId=DishId, Category=value };
			context.Entry(rel).State = System.Data.EntityState.Added;
			context.SaveChanges();
			value.DishCategorieses= null;

			return Request.CreateResponse(HttpStatusCode.OK, value);
		}

		// PUT api/dishcategoriesdishcategory/
		public HttpResponseMessage Put([FromBody]DishCategory value)
		{
			ServerValidationInfo vi = null;
			value.UpdateDate=DateTime.Now;
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

		// DELETE api/dishcategoriesdishcategory/
		public bool Delete(int DishId, string filter = null)
		{
			IQueryable<DishCategories> query = context.DishCategorieses.Where(x=>x.DishId == DishId);
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

		// GET api/dishcategoriesdishcategory/count
		[HttpGet]
		public int Count(int DishId,string filter = null, bool distint=false)
		{
			if(!distint)
			{
				var query = context.DishCategorieses.AsNoTracking().Where(x=>x.DishId == DishId);
				if (filter != null)
				{
					query = query.Where(queryBuider.CreateWhere(filter));
				}
				return query.Count();
			}
			else
			{
				var query = context.DishCategories.AsNoTracking().Where(x => !x.DishCategorieses.Any(y => y.DishId == DishId));
				if (filter != null)
				{
					query = query.Where(distintQueryBuider.CreateWhere(filter));
				}
				return query.Count();
			}
		}

		// GET api/dishcategories/find
		[HttpGet]
		public object Find(string filter)
		{
			return context.DishCategorieses.AsNoTracking()
				.Where(queryBuider.CreateWhere(filter))
				.Select(x=> new
				{
					Id=x.Category.Id,
					Code=x.Category.Code,
					Name=x.Category.Name,
					UpdateUserId=x.Category.UpdateUserId,
					UpdateUserName=x.Category.UpdateUser.Name,
					UpdateDate=x.Category.UpdateDate,
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
