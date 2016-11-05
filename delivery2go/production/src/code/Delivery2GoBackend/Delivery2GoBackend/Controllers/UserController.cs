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
	public class UserController : ApiController
	{

		deliveryContext context;
		QueryBuilder<User> queryBuider;

		public UserController()
		{
			context = new deliveryContext();
			queryBuider = new QueryBuilder<User>();
		}

		// GET api/user/get/
		public System.Collections.ICollection Get(string filter = null, string orderby = null, int top = -1, int skip = -1)
		{
			if (orderby == null)
			{
				orderby = "Id";
			}
			return queryBuider.CreateQuery(context.Users.AsNoTracking(), filter, orderby, skip, top).ToList();
		}

		// GET api/user/get/5
		public object Get(int id)
		{
			return context.Users.AsNoTracking()
				.Where(x=>x.Id == id)
			.FirstOrDefault();
		}

		// POST api/user/
		public HttpResponseMessage Post([FromBody]User value)
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
			context.Entry(value).State = System.Data.EntityState.Added;
			context.SaveChanges();

			return Request.CreateResponse(HttpStatusCode.OK, value);
		}

		// PUT api/user/
		public HttpResponseMessage Put([FromBody]User value, string filter = null)
		{
			ServerValidationInfo vi = null;
			value.CreateDate=DateTime.Now;
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
				var old = context.Users.SingleOrDefault(queryBuider.CreateWhere(filter));
				old.Id = value.Id;
				old.Name = value.Name;
				old.Username = value.Username;
				old.Password = value.Password;
				old.SessionId = value.SessionId;
				old.Mobile = value.Mobile;
				old.Phone = value.Phone;
				old.Adress = value.Adress;
				old.IsActive = value.IsActive;
				old.CreateDate = value.CreateDate;
				old.UpdateDate = value.UpdateDate;
				old.Superadmin = value.Superadmin;
			}
			if (vi != null && vi.ContainsError)
				return Request.CreateResponse(HttpStatusCode.BadRequest, vi);
			var result = context.SaveChanges() > 0;
			return Request.CreateResponse(HttpStatusCode.OK, result);
		}

		// DELETE api/user/
		public bool Delete(string filter = null)
		{
			IQueryable<User> query = context.Users;
			if (filter != null)
			{
				query = query.Where(queryBuider.CreateWhere(filter));
			}
			foreach (var item in query)
			{
				context.Users.Remove(item);
			}
			return context.SaveChanges() > 0;
		}

		// GET api/user/count
		[HttpGet]
		public int Count(string filter = null)
		{
			IQueryable<User> query = context.Users.AsNoTracking();
			if (filter != null)
			{
				query = query.Where(queryBuider.CreateWhere(filter));
			}
			return query.Count();
		}

		// GET api/user/find
		[HttpGet]
		public object Find(string filter)
		{
			return context.Users.AsNoTracking()
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
