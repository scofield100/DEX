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
	public class UserEntitiesUserController : ApiController
	{

		deliveryContext context;
		QueryBuilder<UserEntities> queryBuider;
		QueryBuilder<User> distintQueryBuider;

		public UserEntitiesUserController()
		{
			context = new deliveryContext();
			queryBuider = new QueryBuilder<UserEntities>()
				.MapMember("Id", x => x.User.Id)
				.MapMember("Name", x => x.User.Name)
				.MapMember("Username", x => x.User.Username)
				.MapMember("Password", x => x.User.Password)
				.MapMember("SessionId", x => x.User.SessionId)
				.MapMember("Mobile", x => x.User.Mobile)
				.MapMember("Phone", x => x.User.Phone)
				.MapMember("Adress", x => x.User.Adress)
				.MapMember("IsActive", x => x.User.IsActive)
				.MapMember("CreateDate", x => x.User.CreateDate)
				.MapMember("UpdateDate", x => x.User.UpdateDate)
				.MapMember("Superadmin", x => x.User.Superadmin);

			distintQueryBuider = new QueryBuilder<User>()
;
		}

		// GET api/userentitiesuser/get/
		public System.Collections.ICollection Get(int EntityId, string filter = null, string orderby = null, int top = -1, int skip = -1, bool distint=false)
		{
			if (orderby == null)
			{
				orderby = "Id";
			}
			if(!distint)
			{
				return queryBuider.CreateQuery(context.UserEntitieses.AsNoTracking().Where(x=>x.EntityId == EntityId), filter, orderby, skip, top, x => new
					{
						Id=x.User.Id,
						Name=x.User.Name,
						Username=x.User.Username,
						Password=x.User.Password,
						SessionId=x.User.SessionId,
						Mobile=x.User.Mobile,
						Phone=x.User.Phone,
						Adress=x.User.Adress,
						IsActive=x.User.IsActive,
						CreateDate=x.User.CreateDate,
						UpdateDate=x.User.UpdateDate,
						Superadmin=x.User.Superadmin,
				}).ToList();
			}
			else
			{
				return distintQueryBuider.CreateQuery(context.Users.AsNoTracking().Where(x => !x.UserEntitieses.Any(y => y.EntityId == EntityId)), filter, orderby, skip, top, x => new
					{
						Id=x.Id,
						Name=x.Name,
						Username=x.Username,
						Password=x.Password,
						SessionId=x.SessionId,
						Mobile=x.Mobile,
						Phone=x.Phone,
						Adress=x.Adress,
						IsActive=x.IsActive,
						CreateDate=x.CreateDate,
						UpdateDate=x.UpdateDate,
						Superadmin=x.Superadmin,
				}).ToList();
			}
		}

		// GET api/UserEntitiesUser/get/5
		public object Get(int id, int EntityId)
		{
			return context.UserEntitieses.AsNoTracking().Where(x=>x.EntityId == EntityId && x.User.Id == id)
				.Select(x=> new
				{
					Id=x.User.Id,
					Name=x.User.Name,
					Username=x.User.Username,
					Password=x.User.Password,
					SessionId=x.User.SessionId,
					Mobile=x.User.Mobile,
					Phone=x.User.Phone,
					Adress=x.User.Adress,
					IsActive=x.User.IsActive,
					CreateDate=x.User.CreateDate,
					UpdateDate=x.User.UpdateDate,
					Superadmin=x.User.Superadmin,
				})
			.FirstOrDefault();
		}

		// POST api/userentities/
		public HttpResponseMessage Post(int EntityId, [FromBody]User value)
		{
			ServerValidationInfo vi = null;
			value.CreateDate=DateTime.Now;
			value.UpdateDate=DateTime.Now;
			if (!ModelState.IsValid)
			{
				vi = new ServerValidationInfo(ModelState);
			}
			if (vi != null && vi.ContainsError)
				return Request.CreateResponse(HttpStatusCode.BadRequest, vi);
			var rel = new UserEntities(){ EntityId=EntityId, User=value };
			context.Entry(rel).State = System.Data.EntityState.Added;
			context.SaveChanges();
			value.UserEntitieses= null;

			return Request.CreateResponse(HttpStatusCode.OK, value);
		}

		// PUT api/userentitiesuser/
		public HttpResponseMessage Put([FromBody]User value)
		{
			ServerValidationInfo vi = null;
			value.CreateDate=DateTime.Now;
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

		// DELETE api/userentitiesuser/
		public bool Delete(int EntityId, string filter = null)
		{
			IQueryable<UserEntities> query = context.UserEntitieses.Where(x=>x.EntityId == EntityId);
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

		// GET api/userentitiesuser/count
		[HttpGet]
		public int Count(int EntityId,string filter = null, bool distint=false)
		{
			if(!distint)
			{
				var query = context.UserEntitieses.AsNoTracking().Where(x=>x.EntityId == EntityId);
				if (filter != null)
				{
					query = query.Where(queryBuider.CreateWhere(filter));
				}
				return query.Count();
			}
			else
			{
				var query = context.Users.AsNoTracking().Where(x => !x.UserEntitieses.Any(y => y.EntityId == EntityId));
				if (filter != null)
				{
					query = query.Where(distintQueryBuider.CreateWhere(filter));
				}
				return query.Count();
			}
		}

		// GET api/userentities/find
		[HttpGet]
		public object Find(string filter)
		{
			return context.UserEntitieses.AsNoTracking()
				.Where(queryBuider.CreateWhere(filter))
				.Select(x=> new
				{
					Id=x.User.Id,
					Name=x.User.Name,
					Username=x.User.Username,
					Password=x.User.Password,
					SessionId=x.User.SessionId,
					Mobile=x.User.Mobile,
					Phone=x.User.Phone,
					Adress=x.User.Adress,
					IsActive=x.User.IsActive,
					CreateDate=x.User.CreateDate,
					UpdateDate=x.User.UpdateDate,
					Superadmin=x.User.Superadmin,
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
