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
	public class DishReviewController : ApiController
	{

		deliveryContext context;
		QueryBuilder<DishReview> queryBuider;

		public DishReviewController()
		{
			context = new deliveryContext();
			queryBuider = new QueryBuilder<DishReview>()
				.MapMember("ClientName", x => x.Client.Name)
				.MapMember("DishName", x => x.Dish.Name);
		}

		// GET api/dishreview/get/
		public System.Collections.ICollection Get(string filter = null, string orderby = null, int top = -1, int skip = -1)
		{
			if (orderby == null)
			{
				orderby = "Id";
			}
			return queryBuider.CreateQuery(context.DishReviews.AsNoTracking(), filter, orderby, skip, top, x => new
				{
					Id=x.Id,
					Rating=x.Rating,
					ClientId=x.ClientId,
					ClientName=x.Client.Name,
					DishId=x.DishId,
					DishName=x.Dish.Name,
					Description=x.Description,
					UpdateDate=x.UpdateDate,
				}).ToList();
		}

		// GET api/dishreview/get/5
		public object Get(int id)
		{
			return context.DishReviews.AsNoTracking()
				.Where(x=>x.Id == id)
				.Select(x=> new
				{
					Id=x.Id,
					Rating=x.Rating,
					ClientId=x.ClientId,
					ClientName=x.Client.Name,
					DishId=x.DishId,
					DishName=x.Dish.Name,
					Description=x.Description,
					UpdateDate=x.UpdateDate,
				})
			.FirstOrDefault();
		}

		// POST api/dishreview/
		public HttpResponseMessage Post([FromBody]DishReview value)
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

		// PUT api/dishreview/
		public HttpResponseMessage Put([FromBody]DishReview value, string filter = null)
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
				var old = context.DishReviews.SingleOrDefault(queryBuider.CreateWhere(filter));
				old.Id = value.Id;
				old.Rating = value.Rating;
				old.ClientId = value.ClientId;
				old.DishId = value.DishId;
				old.Description = value.Description;
				old.UpdateDate = value.UpdateDate;
			}
			if (vi != null && vi.ContainsError)
				return Request.CreateResponse(HttpStatusCode.BadRequest, vi);
			var result = context.SaveChanges() > 0;
			return Request.CreateResponse(HttpStatusCode.OK, result);
		}

		// DELETE api/dishreview/
		public bool Delete(string filter = null)
		{
			IQueryable<DishReview> query = context.DishReviews;
			if (filter != null)
			{
				query = query.Where(queryBuider.CreateWhere(filter));
			}
			foreach (var item in query)
			{
				context.DishReviews.Remove(item);
			}
			return context.SaveChanges() > 0;
		}

		// GET api/dishreview/count
		[HttpGet]
		public int Count(string filter = null)
		{
			IQueryable<DishReview> query = context.DishReviews.AsNoTracking();
			if (filter != null)
			{
				query = query.Where(queryBuider.CreateWhere(filter));
			}
			return query.Count();
		}

		// GET api/dishreview/find
		[HttpGet]
		public object Find(string filter)
		{
			return context.DishReviews.AsNoTracking()
				.Where(queryBuider.CreateWhere(filter))
				.Select(x=> new
				{
					Id=x.Id,
					Rating=x.Rating,
					ClientId=x.ClientId,
					ClientName=x.Client.Name,
					DishId=x.DishId,
					DishName=x.Dish.Name,
					Description=x.Description,
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
