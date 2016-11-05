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
	public class UserEntitiesController : ApiController
	{

		deliveryContext context;
		QueryBuilder<UserEntities> queryBuider;

		public UserEntitiesController()
		{
			context = new deliveryContext();
			queryBuider = new QueryBuilder<UserEntities>()
				.MapMember("UserName", x => x.User.Name)
				.MapMember("EntityName", x => x.Entity.Name);
		}

		// GET api/userentities/get/
		public System.Collections.ICollection Get(string filter = null, string orderby = null, int top = -1, int skip = -1)
		{
			if (orderby == null)
			{
				orderby = "UserId,EntityId";
			}
			return queryBuider.CreateQuery(context.UserEntitieses.AsNoTracking(), filter, orderby, skip, top, x => new
				{
					UserId=x.UserId,
					UserName=x.User.Name,
					EntityId=x.EntityId,
					EntityName=x.Entity.Name,
				}).ToList();
		}

		// GET api/userentities/get/5
		public object Get(int id)
		{
			return context.UserEntitieses.AsNoTracking()
				.Where(x=>x.UserId == id)
				.Select(x=> new
				{
					UserId=x.UserId,
					UserName=x.User.Name,
					EntityId=x.EntityId,
					EntityName=x.Entity.Name,
				})
			.FirstOrDefault();
		}

		// POST api/userentities/
		public HttpResponseMessage Post([FromBody]UserEntities value)
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

		// PUT api/userentities/
		public HttpResponseMessage Put([FromBody]UserEntities value, string filter = null)
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
				var old = context.UserEntitieses.SingleOrDefault(queryBuider.CreateWhere(filter));
				old.UserId = value.UserId;
				old.EntityId = value.EntityId;
			}
			if (vi != null && vi.ContainsError)
				return Request.CreateResponse(HttpStatusCode.BadRequest, vi);
			var result = context.SaveChanges() > 0;
			return Request.CreateResponse(HttpStatusCode.OK, result);
		}

		// DELETE api/userentities/
		public bool Delete(string filter = null)
		{
			IQueryable<UserEntities> query = context.UserEntitieses;
			if (filter != null)
			{
				query = query.Where(queryBuider.CreateWhere(filter));
			}
			foreach (var item in query)
			{
				context.UserEntitieses.Remove(item);
			}
			return context.SaveChanges() > 0;
		}

		// GET api/userentities/count
		[HttpGet]
		public int Count(string filter = null)
		{
			IQueryable<UserEntities> query = context.UserEntitieses.AsNoTracking();
			if (filter != null)
			{
				query = query.Where(queryBuider.CreateWhere(filter));
			}
			return query.Count();
		}

		// GET api/userentities/find
		[HttpGet]
		public object Find(string filter)
		{
			return context.UserEntitieses.AsNoTracking()
				.Where(queryBuider.CreateWhere(filter))
				.Select(x=> new
				{
					UserId=x.UserId,
					UserName=x.User.Name,
					EntityId=x.EntityId,
					EntityName=x.Entity.Name,
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
