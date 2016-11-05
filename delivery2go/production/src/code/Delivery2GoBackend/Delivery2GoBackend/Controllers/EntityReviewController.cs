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
	public class EntityReviewController : ApiController
	{

		deliveryContext context;
		QueryBuilder<EntityReview> queryBuider;

		public EntityReviewController()
		{
			context = new deliveryContext();
			queryBuider = new QueryBuilder<EntityReview>()
				.MapMember("ClientName", x => x.Client.Name)
				.MapMember("EntityName", x => x.Entity.Name);
		}

		// GET api/entityreview/get/
		public System.Collections.ICollection Get(string filter = null, string orderby = null, int top = -1, int skip = -1)
		{
			if (orderby == null)
			{
				orderby = "Id";
			}
			return queryBuider.CreateQuery(context.EntityReviews.AsNoTracking(), filter, orderby, skip, top, x => new
				{
					Id=x.Id,
					Rating=x.Rating,
					ClientId=x.ClientId,
					ClientName=x.Client.Name,
					EntityId=x.EntityId,
					EntityName=x.Entity.Name,
					Description=x.Description,
					UpdateDate=x.UpdateDate,
				}).ToList();
		}

		// GET api/entityreview/get/5
		public object Get(int id)
		{
			return context.EntityReviews.AsNoTracking()
				.Where(x=>x.Id == id)
				.Select(x=> new
				{
					Id=x.Id,
					Rating=x.Rating,
					ClientId=x.ClientId,
					ClientName=x.Client.Name,
					EntityId=x.EntityId,
					EntityName=x.Entity.Name,
					Description=x.Description,
					UpdateDate=x.UpdateDate,
				})
			.FirstOrDefault();
		}

		// POST api/entityreview/
		public HttpResponseMessage Post([FromBody]EntityReview value)
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

		// PUT api/entityreview/
		public HttpResponseMessage Put([FromBody]EntityReview value, string filter = null)
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
				var old = context.EntityReviews.SingleOrDefault(queryBuider.CreateWhere(filter));
				old.Id = value.Id;
				old.Rating = value.Rating;
				old.ClientId = value.ClientId;
				old.EntityId = value.EntityId;
				old.Description = value.Description;
				old.UpdateDate = value.UpdateDate;
			}
			if (vi != null && vi.ContainsError)
				return Request.CreateResponse(HttpStatusCode.BadRequest, vi);
			var result = context.SaveChanges() > 0;
			return Request.CreateResponse(HttpStatusCode.OK, result);
		}

		// DELETE api/entityreview/
		public bool Delete(string filter = null)
		{
			IQueryable<EntityReview> query = context.EntityReviews;
			if (filter != null)
			{
				query = query.Where(queryBuider.CreateWhere(filter));
			}
			foreach (var item in query)
			{
				context.EntityReviews.Remove(item);
			}
			return context.SaveChanges() > 0;
		}

		// GET api/entityreview/count
		[HttpGet]
		public int Count(string filter = null)
		{
			IQueryable<EntityReview> query = context.EntityReviews.AsNoTracking();
			if (filter != null)
			{
				query = query.Where(queryBuider.CreateWhere(filter));
			}
			return query.Count();
		}

		// GET api/entityreview/find
		[HttpGet]
		public object Find(string filter)
		{
			return context.EntityReviews.AsNoTracking()
				.Where(queryBuider.CreateWhere(filter))
				.Select(x=> new
				{
					Id=x.Id,
					Rating=x.Rating,
					ClientId=x.ClientId,
					ClientName=x.Client.Name,
					EntityId=x.EntityId,
					EntityName=x.Entity.Name,
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
